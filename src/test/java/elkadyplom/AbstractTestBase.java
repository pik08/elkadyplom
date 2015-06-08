package elkadyplom;

import elkadyplom.dto.AssignmentDto;
import elkadyplom.dto.TopicDto;
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
        return getTopic(supervisor, null, null, true);
    }

    protected Topic getTopic(User supervisor, User student) {
        return getTopic(supervisor, student, "test title", true);
    }

    protected Topic getTopic(User supervisor, String title) {
        return getTopic(supervisor, null, title, true);
    }

    protected Topic getTopic(User supervisor, User student, boolean confirmed) {
        return getTopic(supervisor, student, null, confirmed);
    }

    protected Topic getTopic(User supervisor, User student, String title, boolean confirmed) {
        Topic topic = new Topic();
        topic.setTitle( (title == null ? "test title" : title ) );
        if (student != null) {
            topic.setStudent(student);
        }
        topic.setSupervisor(supervisor);
        topic.setConfirmed(confirmed);
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

    protected void assertTopicDtoEqualsTopic(Topic expectedTopic, TopicDto actualTopic) {
        assertEquals(expectedTopic.getTitle(), actualTopic.getTitle());
        assertEquals(expectedTopic.getThesisType(), actualTopic.getThesisType());
        assertEquals(expectedTopic.getSupervisorName(), actualTopic.getSupervisorName());
        assertEquals(expectedTopic.getSupervisorId(), actualTopic.getSupervisorId());
        assertEquals(expectedTopic.getStudentId(), actualTopic.getStudentId());
        assertEquals(expectedTopic.getStudentName(), actualTopic.getStudentName());
        assertEquals(expectedTopic.getDescription(), actualTopic.getDescription());
    }
}
