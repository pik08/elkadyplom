package elkadyplom;

import elkadyplom.dto.TopicDto;
import elkadyplom.model.Role;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import elkadyplom.repository.TopicsRepository;
import elkadyplom.repository.UserRepository;
import elkadyplom.service.TopicService;
import elkadyplom.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
public class TopicServiceTest extends AbstractTestBase {



    @Test
    public void saveAndFindAllTopicTest() throws Exception {
        User supervisor = getSupervisor("sup");
        Topic topic = getTopic(supervisor);

        List<TopicDto> topics = topicService.findAll(0, 2).getTopics();
        assertEquals(1, topics.size());
        assertTopicDtoEqualsTopic(topics.get(0), topic);
    }

    @Test
    public void findBySupervisorTest() throws Exception {
        User supervisor = getSupervisor("sup");
        Topic topic = getTopic(supervisor);

        List<TopicDto> topics = topicService.findBySupervisor(0, 2, supervisor.getEmail()).getTopics();
        assertEquals(1, topics.size());
        assertTopicDtoEqualsTopic(topics.get(0), topic);
    }

    @Test
    public void findByAssignedStudentTest() throws Exception {
        User supervisor = getSupervisor("sup");
        User student = getStudent("stud");
        Topic topic = getTopic(supervisor, student);

        List<TopicDto> topics = topicService.findByAssignedStudent(0, 2, student.getEmail()).getTopics();
        assertEquals("topics size has to be equal 1", 1, topics.size());
        assertTopicDtoEqualsTopic(topics.get(0), topic);
    }

    private void assertTopicDtoEqualsTopic(TopicDto topic, Topic topic2) {
        assertEquals("titles have to be equal", topic.getTitle(), topic2.getTitle());
        assertEquals(topic.getThesisType(), topic2.getThesisType());
        assertEquals(topic.getSupervisorName(), topic2.getSupervisorName());
        assertEquals(topic.getSupervisorId(), topic2.getSupervisorId());
        assertEquals(topic.getStudentId(), topic2.getStudentId());
        assertEquals(topic.getStudentName(), topic2.getStudentName());
        assertEquals(topic.getDescription(), topic2.getDescription());
    }


}
