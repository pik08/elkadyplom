package elkadyplom;

import elkadyplom.dto.AssignmentDto;
import elkadyplom.model.Role;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import elkadyplom.repository.CumulativeAverageRepository;
import elkadyplom.repository.DeclarationRepository;
import elkadyplom.repository.TopicsRepository;
import elkadyplom.repository.UserRepository;
import elkadyplom.service.DeclarationService;
import elkadyplom.service.TopicService;
import elkadyplom.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

abstract public class AbstractTestBase {

    @Autowired
    protected TopicService topicService;
    @Autowired
    protected TopicsRepository topicsRepository;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected DeclarationService declarationService;
    @Autowired
    protected DeclarationRepository declarationRepository;
    @Autowired
    protected CumulativeAverageRepository cumulativeAverageRepository;

    @Before
    public void setUp() throws Exception {
        declarationRepository.deleteAll();
        cumulativeAverageRepository.deleteAll();
        topicsRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected Topic getTopic(User supervisor) {
        return getTopic(supervisor, null);
    }

    protected Topic getTopic(User supervisor, User student) {
        Topic topic = new Topic();
        topic.setTitle("test title");
        if (student != null) {
            topic.setStudent(student);
        }
        topic.setSupervisor(supervisor);
        topic.setConfirmed(true);
        topic = topicsRepository.save(topic);
        assertNotEquals(topic.getId(), 0);
        return topic;
    }

    protected User getStudent(String name) {
        User student = new User();
        student.setPassword(name);
        student.setEmail(name);
        student.setEnabled("true");
        student.setName(name);
        student.setRole(Role.ROLE_STUDENT);
        student = userRepository.save(student);
        assertNotEquals(student.getId(), 0);
        return student;
    }

    protected User getSupervisor(String name) {
        User supervisor = new User();
        supervisor.setPassword(name);
        supervisor.setEmail(name);
        supervisor.setEnabled("true");
        supervisor.setName(name);
        supervisor.setRole(Role.ROLE_SUPERVISOR);
        supervisor = userRepository.save(supervisor);
        assertNotEquals(supervisor.getId(), 0);
        return supervisor;
    }
}
