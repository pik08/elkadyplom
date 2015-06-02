package elkadyplom.service;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.dto.DeclarationDto;
import elkadyplom.dto.TopicDto;
import elkadyplom.model.Declaration;
import elkadyplom.model.Role;
import elkadyplom.model.Topic;
import elkadyplom.dto.TopicListDto;
import elkadyplom.model.User;
import elkadyplom.repository.DeclarationRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeclarationRepository declarationRepository;

    @Transactional(readOnly = true)
    public TopicListDto findAll(int page, int maxResults) {
        Page<Topic> result = executeQueryFindAll(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindAll(lastPage, maxResults);
        }

        return buildResult(result);
    }

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

    public void save(Topic topic) {
        topicsRepository.save(topic);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int topicId) {
        topicsRepository.delete(topicId);
    }



    @Transactional(readOnly = true)
    public TopicListDto findByKeyword(int page, int maxResults, String name) {
        Page<Topic> result = executeQueryFindByKeyword(page, maxResults, name);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByKeyword(lastPage, maxResults, name);
        }

        return buildResult(result);
    }

    @Transactional(readOnly = true)
    public TopicListDto findByKeywordForSupervisor(int page, int maxResults, String name, String supervisorEmail) {
        User supervisor = userRepository.findByEmail(supervisorEmail);
        if (supervisor == null)
            return null;

        Page<Topic> result = executeQueryFindByKeywordAndSupervisor(page, maxResults, name, supervisor);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByKeywordAndSupervisor(lastPage, maxResults, name, supervisor);
        }

        return buildResult(result);
    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Topic> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private Page<Topic> executeQueryFindAll(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return topicsRepository.findAll(pageRequest);
    }

    private Page<Topic> executeQueryFindBySupervisor(int page, int maxResults, User supervisor) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return topicsRepository.findBySupervisor(pageRequest, supervisor);
    }


    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "title");
    }

    private TopicListDto buildResult(Page<Topic> result) {
        return new TopicListDto(result.getTotalPages(), result.getTotalElements(), result.getContent());
    }

    private Page<Topic> executeQueryFindByKeyword(int page, int maxResults, String name) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return topicsRepository.findByTitleLike(pageRequest, "%" + name + "%");
    }

    private Page<Topic> executeQueryFindByKeywordAndSupervisor(int page, int maxResults, String name, User supervisor) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return topicsRepository.findByTitleAndSupervisor("%" + name + "%", supervisor, pageRequest);
    }

    private boolean isUserAfterOrOnLastPage(int page, Page<Topic> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Topic> result) {
        return result.getTotalElements() > 0;
    }

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


    public List<BasicUserDto> getSupervisorList() {
        List<User> list = userRepository.getUserListByRole(Role.ROLE_SUPERVISOR);
        List<BasicUserDto> supervisorList = new ArrayList<BasicUserDto>();

        for (User u : list)
            supervisorList.add(new BasicUserDto(u));

        return supervisorList;
    }


    public List<BasicUserDto> getStudentList() {
        List<User> list = userRepository.getUserListByRole(Role.ROLE_STUDENT);
        List<BasicUserDto> studentList = new ArrayList<BasicUserDto>();

        for (User u : list)
            studentList.add(new BasicUserDto(u));

        return studentList;
    }

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

    @Transactional
    public boolean saveDeclarations(List<DeclarationDto> declarationDtoList, String studentEmail) {
        if (declarationDtoList == null)
            return false;

        User student = userRepository.findByEmail(studentEmail);
        if (student == null || !student.isStudent())
            return false;

        Topic topic;
        for (DeclarationDto dto : declarationDtoList) {
            if (dto == null)
                return false;

            if (dto.getDeclarationId() > 0) {
                // istnieje już taka deklaracja
                Declaration d = declarationRepository.findOne(dto.getDeclarationId());
                if (d == null)
                    return false;
                d.setRank(dto.getRank());   // tylko to się może zmienić
                declarationRepository.save(d);
            } else {
                // tworzymy nową deklarację
                topic = topicsRepository.findOne(dto.getTopicId());
                if (topic == null)
                    return false;
                Declaration d = new Declaration(student, topic, dto.getRank());
                declarationRepository.save(d);
            }
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
}
