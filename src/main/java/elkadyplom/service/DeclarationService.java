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

@Service
@Transactional
public class DeclarationService {

    private final int SUPERVISED_STUDENTS_LIMIT = 5;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeclarationRepository declarationRepository;

    @Autowired
    private CumulativeAverageRepository cumulativeAverageRepository;


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

    public List<DeclarationDto> getDeclarationDtos(String currentUserEmail) {
        if (currentUserEmail == null)
            return null;

        User student = userRepository.findByEmail(currentUserEmail);
        if (student == null || !student.isStudent())
            return null;

        List<Declaration> declarations = declarationRepository.findByStudent(student);
        if (declarations == null)
            return null;

        List<DeclarationDto> dtoList = new ArrayList<DeclarationDto>();
        for (Declaration d : declarations)
            dtoList.add(new DeclarationDto(d));

        return dtoList;
    }

    @Secured("ROLE_ADMIN")
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

            List<Declaration> declarations = declarationRepository.findByStudent(avg.getStudent());
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
                break;
            }

            assignmentsList.add(new AssignmentDto(avg, topicsList));
        }

        return assignmentsList;
    }

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
