package elkadyplom;

import elkadyplom.controller.TopicsController;
import elkadyplom.dto.TopicListDto;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@PreAuthorize("authenticated")

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
public class TopicsControllerTest extends AbstractTestBase {

  public static final Locale LOCALE = new Locale("pl");
  @Autowired
  TopicsController topicsController;

  @org.junit.Test
  public void searchTest() throws Exception {
    String emailStudent1 = "email_student1";
    String emailStudent2 = "email_student2";
    String emailAdmin = "email_admin";
    String emailSupervisor1 = "email_supervisor1";
    String emailSupervisor2 = "email_supervisor2";

    User student1 = getStudent(emailStudent1);
    User student2 = getStudent(emailStudent2);
    User supervisor1 = getSupervisor(emailSupervisor1);
    User supervisor2 = getSupervisor(emailSupervisor2);
    Topic topic1 = getTopic(supervisor1, "title1");
    Topic topic2 = getTopic(supervisor2, student2, "title1", true);
    Topic topic3 = getTopic(supervisor2, "title2");

    setAuth("ROLE_ADMIN", emailAdmin);
    ResponseEntity response = topicsController.search(topic1.getTitle(), 0, LOCALE);
    clearAuth();

    TopicListDto topicListDto = (TopicListDto) response.getBody();
    Assert.assertEquals(2, topicListDto.getTopics().size());
    Assert.assertEquals(org.springframework.http.HttpStatus.OK, response.getStatusCode());


    setAuth("ROLE_SUPERVISOR", emailSupervisor1);
    ResponseEntity response2 = topicsController.search(topic1.getTitle(), 0, LOCALE);
    clearAuth();

    TopicListDto topicListDto2 = (TopicListDto) response2.getBody();
    Assert.assertEquals(1, topicListDto2.getTopics().size());
    assertTopicDtoEqualsTopic(topic1, topicListDto2.getTopics().get(0));
    Assert.assertEquals(org.springframework.http.HttpStatus.OK, response2.getStatusCode());

    setAuth("ROLE_SUPERVISOR", emailSupervisor2);
    ResponseEntity response3 = topicsController.search(topic2.getTitle(), 0, LOCALE);
    clearAuth();

    TopicListDto topicListDto3 = (TopicListDto) response3.getBody();
    Assert.assertEquals(1, topicListDto3.getTopics().size());
    assertTopicDtoEqualsTopic(topic2, topicListDto3.getTopics().get(0));
    Assert.assertEquals(org.springframework.http.HttpStatus.OK, response3.getStatusCode());


    setAuth("ROLE_STUDENT", emailStudent1);
    ResponseEntity response4 = topicsController.search(topic1.getTitle(), 0, LOCALE);
    clearAuth();

    TopicListDto topicListDto4 = (TopicListDto) response4.getBody();
    Assert.assertEquals(1, topicListDto4.getTopics().size());
    assertTopicDtoEqualsTopic(topic1, topicListDto4.getTopics().get(0));
    Assert.assertEquals(org.springframework.http.HttpStatus.OK, response4.getStatusCode());
  }

  private void setAuth(String role, String email) {
    Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    roles.add(new SimpleGrantedAuthority(role));
    Authentication auth = new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(
        email, "pass", roles), "user", roles);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(auth);
  }

  private void clearAuth() {
    SecurityContextHolder.clearContext();
  }
}
