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
public class TopicServiceTest {

  @Autowired
  private TopicService topicService;
  @Autowired
  private TopicsRepository topicsRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @Before
  public void setUp() throws Exception {
    topicsRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  public void saveAndFindAllTopicTest() throws Exception {
    User supervisor = getSupervisor();
    Topic topic = getTopic(supervisor, null);

    List<TopicDto> topics = topicService.findAll(0, 2).getTopics();
    assertEquals(1, topics.size());
    assertTopicDtoEqualsTopic(topics.get(0), topic);
  }

  @Test
  public void findBySupervisorTest() throws Exception {
    User supervisor = getSupervisor();
    Topic topic = getTopic(supervisor, null);

    List<TopicDto> topics = topicService.findBySupervisor(0, 2, supervisor.getEmail()).getTopics();
    assertEquals(1, topics.size());
    assertTopicDtoEqualsTopic(topics.get(0), topic);
  }

  @Test
  public void findByAssignedStudentTest() throws Exception {
    User supervisor = getSupervisor();
    User student = getStudent();
    Topic topic = getTopic(supervisor, student);

    List<TopicDto> topics = topicService.findByAssignedStudent(0, 2, student.getEmail()).getTopics();
    assertEquals(1, topics.size());
    assertTopicDtoEqualsTopic(topics.get(0), topic);
  }

  private void assertTopicDtoEqualsTopic(TopicDto topic, Topic topic2) {
    assertNotEquals(topic.getId(), topic2.getId());
    assertEquals(topic.getTitle(), topic2.getTitle());
    assertEquals(topic.getThesisType(), topic2.getThesisType());
    assertEquals(topic.getSupervisorName(), topic2.getSupervisorName());
    assertEquals(topic.getSupervisorId(), topic2.getSupervisorId());
    assertEquals(topic.getStudentId(), topic2.getStudentId());
    assertEquals(topic.getStudentName(), topic2.getStudentName());
    assertEquals(topic.getDescription(), topic2.getDescription());
  }

  private Topic getTopic(User supervisor, User student) {
    Topic topic = new Topic();
    topic.setTitle("test title");
    if (student != null) {
      student.setId(userService.findByEmail(student.getEmail()).getId());
      topic.setStudent(student);
    }
    supervisor.setId(userService.findByEmail(supervisor.getEmail()).getId());
    topic.setSupervisor(supervisor);
    topicService.save(topic);
    return topic;
  }

  private User getStudent() {
    User student = new User();
    student.setPassword("aaa");
    student.setEmail("bvv");
    student.setEnabled("enabled");
    student.setName("xrr");
    student.setRole(Role.ROLE_STUDENT);
    userService.save(student);
    return student;
  }

  private User getSupervisor() {
    User supervisor = new User();
    supervisor.setPassword("a");
    supervisor.setEmail("b");
    supervisor.setEnabled("enabled");
    supervisor.setName("x");
    supervisor.setRole(Role.ROLE_SUPERVISOR);
    userService.save(supervisor);
    return supervisor;
  }
}
