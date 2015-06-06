package elkadyplom.service;

import elkadyplom.dto.TopicDto;
import elkadyplom.model.Topic;
import elkadyplom.dto.TopicListDto;
import elkadyplom.model.User;
import elkadyplom.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import elkadyplom.repository.TopicsRepository;

/**
 * Warstwa serwisowa, zajmująca się tematami prac dyplomowych.
 */

@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Metoda wyszukująca daną stronę z listy wszystkich tematów w systemie.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @return dto ze stroną tematów
     */
    @Transactional(readOnly = true)
    public TopicListDto findAll(int page, int maxResults) {
        Page<Topic> result = executeQueryFindAll(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindAll(lastPage, maxResults);
        }

        return buildResult(result);
    }

    /**
     * Metoda wyszukująca daną stronę z listy tematów przypisanych do danego promotora.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param supervisorEmail login promotora
     * @return dto ze stroną tematów
     */
    @Transactional(readOnly = true)
    public TopicListDto findBySupervisor(int page, int maxResults, String supervisorEmail) {
        User supervisor = userRepository.findByEmail(supervisorEmail);
        if (supervisor == null)
            return null;

        Page<Topic> result = executeQueryFindBySupervisor(page, maxResults, supervisor);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindBySupervisor(lastPage, maxResults, supervisor);
        }

        return buildResult(result);
    }

    /**
     * Metoda wyszukująca daną stronę z listy tematów przypisanych do danego studenta.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param studentEmail login studenta
     * @return dto ze stroną tematów
     */
    @Transactional(readOnly = true)
    public TopicListDto findByAssignedStudent(int page, int maxResults, String studentEmail) {
        User supervisor = userRepository.findByEmail(studentEmail);
        if (supervisor == null)
            return null;

        Page<Topic> result = executeQueryFindByAssignedStudent(page, maxResults, supervisor);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByAssignedStudent(lastPage, maxResults, supervisor);
        }

        return buildResult(result);
    }

    /**
     * Metoda wyszukująca daną stronę z listy wszystkich potwierdzonych, nieprzypisanych tematów w systemie.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @return dto ze stroną tematów
     */
    public TopicListDto findForStudents(int page, int maxResults) {
        Page<Topic> result = executeQueryFindForStudents(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindForStudents(lastPage, maxResults);
        }

        return buildResult(result);
    }

    /**
     * Metoda zapisująca temat.
     * @param topic temat
     */
    public void save(Topic topic) {
        topicsRepository.save(topic);
    }

    /**
     * Metoda usuwająca temat.
     * @param topicId id tematu
     */
    @Secured("ROLE_ADMIN")
    public void delete(int topicId) {
        topicsRepository.delete(topicId);
    }

    /**
     * Metoda wyszukująca stronę z listy tematów pasujących do danego słowa kluczowego.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param keyword słowo kluczowe
     * @return dto ze stroną tematów
     */
    @Transactional(readOnly = true)
    public TopicListDto findByKeyword(int page, int maxResults, String keyword) {
        Page<Topic> result = executeQueryFindByKeyword(page, maxResults, keyword);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByKeyword(lastPage, maxResults, keyword);
        }

        return buildResult(result);
    }

    /**
     * Metoda wyszukująca stronę z listy tematów pasujących do danego słowa kluczowego, przypisanych do danego promotora.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param keyword słowo kluczowe
     * @param supervisorEmail login promotora
     * @return dto ze stroną tematów
     */
    @Transactional(readOnly = true)
    public TopicListDto findByKeywordForSupervisor(int page, int maxResults, String keyword, String supervisorEmail) {
        User supervisor = userRepository.findByEmail(supervisorEmail);
        if (supervisor == null)
            return null;

        Page<Topic> result = executeQueryFindByKeywordAndSupervisor(page, maxResults, keyword, supervisor);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByKeywordAndSupervisor(lastPage, maxResults, keyword, supervisor);
        }

        return buildResult(result);
    }

    /**
     * Metoda wyszukująca stronę z listy tematów pasujących do danego słowa kluczowego, nieprzypisanych i potwierdzonych.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param keyword słowo kluczowe
     * @return dto ze stroną tematów
     */
    public TopicListDto findByKeywordForStudents(int page, int maxResults, String keyword) {
        Page<Topic> result = executeQueryFindByKeywordForStudents(page, maxResults, keyword);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByKeywordForStudents(lastPage, maxResults, keyword);
        }

        return buildResult(result);
    }

    /**
     * Metoda sprawdzająca, czy należy wyszukać ostatnią stronę w taki sams sposób.
     * @param page numer obecnej strony
     * @param result wynik
     * @return true, jeśli należy tak zrobić
     */
    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Topic> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    /**
     * Wykonuje zapytanie wyszukujące stronę ze wszystkich tematów w systemie.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @return strona z tematami
     */
    private Page<Topic> executeQueryFindAll(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByTitleASC());
        return topicsRepository.findAll(pageRequest);
    }

    /**
     * Wykonuje zapytanie wyszukujące stronę z tematów przypisanych do promotora.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param supervisor promotor
     * @return strona z tematami
     */
    private Page<Topic> executeQueryFindBySupervisor(int page, int maxResults, User supervisor) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByTitleASC());
        return topicsRepository.findBySupervisor(pageRequest, supervisor);
    }

    /**
     * Wykonuje zapytanie wyszukujące stronę z listy tematów przypisanych do studenta.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param student student
     * @return strona z tematami
     */
    private Page<Topic> executeQueryFindByAssignedStudent(int page, int maxResults, User student) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByTitleASC());
        return topicsRepository.findByStudent(pageRequest, student);
    }

    /**
     * Wykonuje zapytanie wyszukujące stronę ze wszystkich nieprzypisanych, potwierdzonych tematów w systemie.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @return strona z tematami
     */
    private Page<Topic> executeQueryFindForStudents(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByTitleASC());
        return topicsRepository.findAllConfirmedNotAssigned(pageRequest);
    }

    /**
     * Metoda tworząca obiekt porządku sortowania alfabetycznie po tytule.
     * @return obiekt sortowania
     */
    private Sort sortByTitleASC() {
        return new Sort(Sort.Direction.ASC, "title");
    }

    /**
     * Metoda tworząca dto ze stroną tematów.
     * @param result strona tematów
     * @return dto
     */
    private TopicListDto buildResult(Page<Topic> result) {
        TopicListDto dto = new TopicListDto(result.getTotalPages(), result.getTotalElements(), result.getContent());
        return dto;
    }

    /**
     * Wykonuje zapytanie wyszukujące stronę z tematami pasującymi do słowa kluczowego.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param keyword słowo kluczowe
     * @return strona z tematami
     */
    private Page<Topic> executeQueryFindByKeyword(int page, int maxResults, String keyword) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByTitleASC());
        return topicsRepository.findByTitleLike(pageRequest, "%" + keyword + "%");
    }

    /**
     * Wykonuje zapytanie wyszukujące stronę z tematami pasującymi do słowa kluczowego, przypisanymi do promotora.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param keyword słowo kluczowe
     * @param supervisor promotor
     * @return strona z tematami
     */
    private Page<Topic> executeQueryFindByKeywordAndSupervisor(int page, int maxResults, String keyword, User supervisor) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByTitleASC());
        return topicsRepository.findByTitleAndSupervisor("%" + keyword + "%", supervisor, pageRequest);
    }

    /**
     * Wykonuje zapytanie wyszukujące stronę z tematami pasującymi do słowa kluczowego,potwierdzonymi, nieprzypisanymi.
     * @param page numer strony
     * @param maxResults liczba wyników na stronie
     * @param keyword słowo kluczowe
     * @return strona z tematami
     */
    private Page<Topic> executeQueryFindByKeywordForStudents(int page, int maxResults, String keyword) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByTitleASC());
        return topicsRepository.findConfirmedNotAssignedByTitleLike("%" + keyword + "%", pageRequest);
    }

    /**
     * Metoda sprawdzająca, czy uzytkownik ogląda ostatnią stronę (albo dalej).
     * @param page numer strony
     * @param result strona z tematami
     * @return true, jeśli tak jest
     */
    private boolean isUserAfterOrOnLastPage(int page, Page<Topic> result) {
        return page >= result.getTotalPages() - 1;
    }

    /**
     * Metoda sprawdzająca, czy są dane w bazie.
     * @param result wynik wyszukiwania w bazie
     * @return true, jeśli są dane
     */
    private boolean hasDataInDataBase(Page<Topic> result) {
        return result.getTotalElements() > 0;
    }

    /**
     * Metoda zapisująca temat do bazy na podstawie dto.
     * @param topicDto dto z danymi tematu
     * @return true, jeśli zapis się powiódł
     */
    public boolean save(TopicDto topicDto) {
        User supervisor = userRepository.findOne(topicDto.getSupervisorId());
        if (supervisor == null || !supervisor.isSupervisor() )
            return false;

        User student = null;
        if (topicDto.getStudentId() != 0) {
            student = userRepository.findOne(topicDto.getStudentId());
            if (student == null || !student.isStudent() )
                return false;
        }

        Topic topic = new Topic(topicDto, supervisor, student);

        save(topic);
        return true;
    }

    /**
     * Metoda zapisująca temat do bazy na podstawie dto; temat jest tworzony przez promotora i do niego przypisywany.
     * @param topicDto dto z danymi tematu
     * @param supervisorEmail login promotora
     * @return true, jeśli zapis się powiódł
     */
    public boolean saveAsSupervisor(TopicDto topicDto, String supervisorEmail) {
        User supervisor = userRepository.findByEmail(supervisorEmail);
        if (supervisor == null || !supervisor.isSupervisor() )
            return false;

        User student = null;
        if (topicDto.getStudentId() != 0) {
            student = userRepository.findOne(topicDto.getStudentId());
            if (student == null || !student.isStudent() )
                return false;
        }

        Topic topic = new Topic(topicDto, supervisor, student);

        save(topic);
        return true;
    }

    /**
     * Metoda edytująca temat na podstawie dto z jego danymi.
     * @param topicDto dto tematu
     * @return true, jeśli edycja się powiodła
     */
    public boolean update(TopicDto topicDto) {
        if (topicDto == null)
            return false;

        Topic topic = topicsRepository.findOne(topicDto.getId());
        if (topic == null)
            return false;

        User supervisor = null, student = null;
        if (topic.getSupervisorId() != topicDto.getSupervisorId()) {
            supervisor = userRepository.findOne(topicDto.getSupervisorId());
            if (supervisor == null || !supervisor.isSupervisor() )
                return false;
        }

        if (topicDto.getStudentId() > 0 && topic.getStudentId() != topicDto.getStudentId() ) {
            student = userRepository.findOne(topicDto.getStudentId());
            if (student == null || !student.isStudent() )
                return false;
        }

        topic.setAll(topicDto, supervisor, student);

        save(topic);
        return true;
    }

    /**
     * Metoda edytująca temat do bazy na podstawie dto; temat jest edytowany przez promotora, musi być niepotwierdzony i do niego przypisany.
     * @param topicDto dto z danymi tematu
     * @param supervisorEmail login promotora
     * @return true, jeśli zapis się powiódł
     */
    public boolean updateBySupervisor(TopicDto topicDto, String supervisorEmail) {
        if (topicDto == null || StringUtils.isEmpty(supervisorEmail))
            return false;

        User supervisor = userRepository.findByEmail(supervisorEmail);
        if (supervisor == null)
            return false;

        Topic topic = topicsRepository.findOne(topicDto.getId());

        // nie możemy edytować zatwierdzonego tematu albo tematu innego promotora
        if (topic == null || topic.isConfirmed() || !supervisor.equals(topic.getSupervisor()) )
            return false;

        User student = null;
        if (topicDto.getStudentId() > 0 && topic.getStudentId() != topicDto.getStudentId() ) {
            student = userRepository.findOne(topicDto.getStudentId());
            if (student == null || !student.isStudent() )
                return false;
        }

        topicDto.setConfirmed(false); // tego nie możemy przestawić jako supervisor
        topic.setAll(topicDto, null, student);

        save(topic);
        return true;
    }

    /**
     * Metoda usuwająca temat przez promotora (musi być przypisany do niego i niepotwierdzony).
     * @param topicId id tematu
     * @param supervisorEmail login promotora
     * @return true, jesli usunięcie się powiedzie
     */
    public boolean deleteBySupervisor(int topicId, String supervisorEmail) {
        Topic topic = topicsRepository.findOne(topicId);
        if (topic == null || topic.isConfirmed())
            return false;

        User supervisor = userRepository.findByEmail(supervisorEmail);
        if (supervisor == null || !supervisor.isSupervisor() || !supervisor.equals(topic.getSupervisor()))
            return false;

        topicsRepository.delete(topicId);
        return true;
    }



}
