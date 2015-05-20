package elkadyplom.service;

import elkadyplom.dto.TopicDto;
import elkadyplom.model.Role;
import elkadyplom.model.Topic;
import elkadyplom.dto.TopicListDto;
import elkadyplom.model.User;
import elkadyplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import elkadyplom.repository.TopicsRepository;

@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public TopicListDto findAll(int page, int maxResults) {
        Page<Topic> result = executeQueryFindAll(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindAll(lastPage, maxResults);
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

//    @Transactional(readOnly = true)
//    public TopicListDto findByNameLike(int page, int maxResults, String name) {
//        Page<Topic> result = executeQueryFindByName(page, maxResults, name);
//
//        if(shouldExecuteSameQueryInLastPage(page, result)){
//            int lastPage = result.getTotalPages() - 1;
//            result = executeQueryFindByName(lastPage, maxResults, name);
//        }
//
//        return buildResult(result);
//    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Topic> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private Page<Topic> executeQueryFindAll(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return topicsRepository.findAll(pageRequest);
    }

    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "title");
    }

    private TopicListDto buildResult(Page<Topic> result) {
        return new TopicListDto(result.getTotalPages(), result.getTotalElements(), result.getContent());
    }

//    private Page<Topic> executeQueryFindByName(int page, int maxResults, String name) {
//        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());
//
//        return topicsRepository.findByNameLike(pageRequest, "%" + name + "%");
//    }

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

        Topic topic = new Topic();
        topic.setSupervisor(supervisor);

        if (topicDto.getStudentId() != 0) {
            User student = userRepository.findOne(topicDto.getStudentId());
            if (student == null || !student.isStudent() )
                return false;
            topic.setStudent(student);
        }

        topic.setTitle(topicDto.getTitle());
        topic.setDescription(topicDto.getDescription());

        save(topic);
        return true;
    }

    public boolean update(TopicDto topicDto) {
        if (topicDto == null)
            return false;

        Topic topic = topicsRepository.findOne(topicDto.getId());
        if (topic == null)
            return false;

        topic.setConfirmed(topicDto.isConfirmed());
        topic.setDescription(topicDto.getDescription());
        topic.setTitle(topicDto.getTitle());

        if (topic.getSupervisorId() != topicDto.getSupervisorId()) {
            User supervisor = userRepository.findOne(topicDto.getSupervisorId());
            if (supervisor == null || !supervisor.isSupervisor() )
                return false;
            topic.setSupervisor(supervisor);
        }

        if (topicDto.getStudentId() > 0 && topic.getStudentId() != topicDto.getStudentId() ) {
            User student = userRepository.findOne(topicDto.getStudentId());
            if (student == null || !student.isStudent() )
                return false;
            topic.setStudent(student);
        }

        save(topic);
        return true;
    }

}