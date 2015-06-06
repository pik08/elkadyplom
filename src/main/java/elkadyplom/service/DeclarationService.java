package elkadyplom.service;

import elkadyplom.dto.AssignmentDto;
import elkadyplom.dto.DeclarationDto;
import elkadyplom.dto.DeclaredTopicDto;
import elkadyplom.model.*;
import elkadyplom.repository.CumulativeAverageRepository;
import elkadyplom.repository.DeclarationRepository;
import elkadyplom.repository.TopicsRepository;
import elkadyplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Warstwa serwisowa, zajmująca się deklaracjami studentów.
 */

@Service
@Transactional
public class DeclarationService {

    /**
     * Maksymalna liczba dyplomantów jednego promotora.
     */
    private final int SUPERVISED_STUDENTS_LIMIT = 5;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeclarationRepository declarationRepository;

    @Autowired
    private CumulativeAverageRepository cumulativeAverageRepository;

    /**
     * Zapisuje deklaracje studenta na podstawie dto z danymi deklaracji.
     * @param declarationDtoList dto z danymi deklaracji
     * @param studentEmail login studenta
     * @return true, jesli zapis się powiódł
     */
    @Transactional
    public boolean saveDeclarations(List<DeclarationDto> declarationDtoList, String studentEmail) {
        if (declarationDtoList == null)
            return false;

        User student = userRepository.findByEmail(studentEmail);
        if (student == null || !student.isStudent())
            return false;

        Topic topic;

        declarationRepository.deleteByStudent(student);

        for (DeclarationDto dto : declarationDtoList) {
            topic = topicsRepository.findOne(dto.getTopicId());
            if (topic == null)
                return false;
            Declaration d = new Declaration(student, topic, dto.getRank());
            declarationRepository.save(d);
        }

        return true;
    }

    /**
     * Wyszukuje dotychczasowe deklaracje studenta i zwraca je w postaci dto.
     * @param studentEmail login studenta
     * @return lista dto z deklaracjami
     */
    public List<DeclarationDto> getDeclarationDtos(String studentEmail) {
        if (studentEmail == null)
            return null;

        User student = userRepository.findByEmail(studentEmail);
        if (student == null || !student.isStudent())
            return null;

        List<Declaration> declarations = declarationRepository.findByStudent(student, new Sort(Sort.Direction.ASC, "rank"));
        if (declarations == null)
            return null;

        List<DeclarationDto> dtoList = new ArrayList<DeclarationDto>();
        for (Declaration d : declarations)
            dtoList.add(new DeclarationDto(d));

        return dtoList;
    }

    /**
     * Metoda zapisująca przyporządkowanie tematów do studentów.
     * @param list lista przyporządkowań tematów do studentów
     * @return true, jeśli zapis się powiedzie
     */
    public boolean saveAssignedTopics(List<AssignmentDto> list) {
        if (list == null)
            return false;

        List<Topic> topicsToSave = new ArrayList<Topic>();
        for (AssignmentDto dto : list) {
            if (dto == null)
                return false;

            User student = userRepository.findOne(dto.getStudentId());
            if (student == null)
                return false;

            Topic topic = null;
            if (dto.getTopics() == null)
                return false;
            for (DeclaredTopicDto t : dto.getTopics()) {
                if (t != null && t.isAssigned()) {
                    topic = topicsRepository.findOne(t.getTopicId());
                    break;
                }
            }
            if (topic == null || !topic.isConfirmed())
                return false;

            topic.setStudent(student);
            topicsToSave.add(topic);
        }

        topicsRepository.save(topicsToSave);
        return true;
    }

    /**
     * Metoda przetwarzająca dane dot. średnich skumulowanych i deklaracji studentów i znajduje przyporządkowanie tematów do studentów.
     *
     * Zasady przyporzadkowania:
     * 1. Z listy dziekańskiej biorę osobę bez przydzielonego tematu z najwyższą średnią skumulowaną.
     * 2. Biorę pierwszy niezaalokowany jeszcze temat pracowni z listy preferencji tej osoby.
     * 3. Jeżeli opiekun proponujący temat nie osiągnął limitu studentów przydzielam osobie niezaalokowany jeszcze temat
     *    pracowni znajdujący się na jej liście preferencji.
     * 4. Jeśli warunek na limit studentów danego opiekuna nie był spełniony usuwam temat z listy preferencji osoby i wracam do punktu 2.
     *
     * @return lista dto z przyporządkowaniami tematów do studentów
     */
    public List<AssignmentDto> assignTopics() {
        Iterable<CumulativeAverage> averages = cumulativeAverageRepository.findAll(new Sort(Sort.Direction.DESC, "average"));
        List<Topic> assignedTopics = topicsRepository.findAssignedConfirmedTopics();
        List<User> supervisors = userRepository.getUserListByRole(Role.ROLE_SUPERVISOR);

        if (averages == null || supervisors == null || supervisors.isEmpty() )
            return null;

        List<AssignmentDto> assignmentsList = new ArrayList<AssignmentDto>();

        // iterowanie po średnich malejąco: kolejni studenci mają coraz niższe średnie
        for (CumulativeAverage avg : averages) {
            if ( hasAssignedTopic(avg.getStudent(), assignedTopics) )
                continue;

            List<Declaration> declarations = declarationRepository.findByStudent(avg.getStudent(), new Sort(Sort.Direction.ASC, "rank"));
            if (declarations == null || declarations.isEmpty())
                continue;

            List<DeclaredTopicDto> topicsList = new ArrayList<DeclaredTopicDto>();
            boolean alreadyAssigned = false;
            for ( Declaration declaration : declarations ) {
                Topic topic = declaration.getTopic();
                if (topic == null)
                    continue;

                if ( alreadyAssigned || assignedTopics.contains(topic) || isSupervisedStudentsLimitReached(topic.getSupervisor(), assignedTopics) ) {
                    topicsList.add(new DeclaredTopicDto(topic));
                    continue;
                }

                alreadyAssigned = true;
                assignedTopics.add(topic);
                topicsList.add(new DeclaredTopicDto(topic, true));
            }

            assignmentsList.add(new AssignmentDto(avg, topicsList));
        }

        return assignmentsList;
    }

    /**
     * Sprawdza, czy dany promotor osiągnął już limit dyplomantów (studentów przypisanych do jego tematów).
     * @param supervisor
     * @param assignedTopics
     * @return
     */
    private boolean isSupervisedStudentsLimitReached(User supervisor, List<Topic> assignedTopics) {
        if (supervisor == null || assignedTopics == null)
            return false;

        int supervisedStudentsNumber = 0;
        for (Topic t : assignedTopics) {
            if (t == null)
                continue;

            if (supervisor.equals(t.getSupervisor()))
                supervisedStudentsNumber++;
        }

        return supervisedStudentsNumber >= SUPERVISED_STUDENTS_LIMIT;
    }

    /**
     * Sprawdza, czy student ma już przypisany do siebie temat na liście przypisanych tematów.
     * @param student stuent
     * @param assignedTopics lista przypisanych tematów
     * @return true, jesli student ma już przypisany temat
     */
    private boolean hasAssignedTopic(User student, List<Topic> assignedTopics) {
        if (student == null || assignedTopics == null)
            return false;

        for (Topic t : assignedTopics) {
            if (t == null)
                continue;
            if ( student.equals(t.getStudent()) )
                return true;
        }

        return false;
    }

}
