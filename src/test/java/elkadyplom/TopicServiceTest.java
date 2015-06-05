package elkadyplom;

import elkadyplom.model.Role;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import elkadyplom.repository.UserRepository;
import elkadyplom.service.TopicService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
public class TopicServiceTest {

  @Autowired
  private TopicService topicService;

  @Autowired
  private UserRepository userRepository;

  @org.junit.Test
  public void findAllTopicTest() throws Exception {
    User supervisor = getSupervisor();

    Topic topic = new Topic();
    topic.setTitle("test title");
    topic.setSupervisor(supervisor);
    topicService.save(topic);

    Assert.assertEquals(topicService.findAll(0, 1).getTopics().get(0).getTitle(), topic.getTitle());
  }

  private User getSupervisor() {
    User supervisor = new User();
    supervisor.setId(1);
    supervisor.setPassword("a");
    supervisor.setEmail("b");
    supervisor.setEnabled("enabled");
    supervisor.setName("x");
    supervisor.setRole(Role.ROLE_SUPERVISOR);
    userRepository.save(supervisor);
    return supervisor;
  }
}
