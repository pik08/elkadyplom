package elkadyplom;

import elkadyplom.dto.TopicDto;
import elkadyplom.dto.TopicListDto;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        assertTopicDtoEqualsTopic(topic, topics.get(0));
    }

    @Test
    public void findBySupervisorTest() throws Exception {
        User supervisor = getSupervisor("sup");
        Topic topic = getTopic(supervisor);

        List<TopicDto> topics = topicService.findBySupervisor(0, 2, supervisor.getEmail()).getTopics();
        assertEquals(1, topics.size());
        assertTopicDtoEqualsTopic(topic, topics.get(0));
    }

    @Test
    public void findByAssignedStudentTest() throws Exception {
        User supervisor = getSupervisor("sup");
        User student = getStudent("stud");
        Topic topic = getTopic(supervisor, student);

        List<TopicDto> topics = topicService.findByAssignedStudent(0, 2, student.getEmail()).getTopics();
        assertEquals("topics size has to be equal 1", 1, topics.size());
        assertTopicDtoEqualsTopic(topic, topics.get(0));
    }

    @Test
    public void findForStudentsTest() {
        User sup = getSupervisor("sup");
        User stud1 = getStudent("stud1");
        User stud2 = getStudent("stud2");
        Topic t1 = getTopic(sup, null, true);   // powinno znaleźć ten,
        Topic t2 = getTopic(sup, null, false);
        Topic t3 = getTopic(sup, stud1, true);
        Topic t4 = getTopic(sup, stud2, false);
        Topic t5 = getTopic(sup, null, true);   // ten
        Topic t6 = getTopic(sup, null, false);
        Topic t7 = getTopic(sup, null, true);   // i ten

        TopicListDto dto = topicService.findForStudents(0, 5);
        assertNotNull(dto.getTopics());
        assertEquals(3, dto.getTopics().size());
        assertTopicDtoEqualsTopic(t1, dto.getTopics().get(0));
        assertTopicDtoEqualsTopic(t5, dto.getTopics().get(1));
        assertTopicDtoEqualsTopic(t7, dto.getTopics().get(2));
    }

    @Test
    public void findByKeywordTest() {
        User sup = getSupervisor("sup");
        Topic t1 = getTopic(sup, "title abc title");
        Topic t2 = getTopic(sup, "title ac");
        Topic t3 = getTopic(sup, "title deabed");
        Topic t4 = getTopic(sup, "title abcde");

        TopicListDto dto = topicService.findByKeyword(0, 5, "abc");
        assertNotNull(dto.getTopics());
        assertEquals(2, dto.getTopics().size());
        assertTopicDtoEqualsTopic(t1, dto.getTopics().get(0));
        assertTopicDtoEqualsTopic(t4, dto.getTopics().get(1));

        dto = topicService.findByKeyword(0, 5, "ab");
        assertNotNull(dto.getTopics());
        assertEquals(3, dto.getTopics().size());
        assertTopicDtoEqualsTopic(t1, dto.getTopics().get(0));
        assertTopicDtoEqualsTopic(t4, dto.getTopics().get(1));
        assertTopicDtoEqualsTopic(t3, dto.getTopics().get(2));
    }

    @Test
    public void findByKeywordForSupervisor() {
        User sup1 = getSupervisor("sup1");
        User sup2 = getSupervisor("sup2");

        Topic t1 = getTopic(sup1, "title qwerty");  // ten
        Topic t2 = getTopic(sup2, "title qwerty");
        Topic t3 = getTopic(sup1, "title qwety");
        Topic t4 = getTopic(sup1, "title werwer");  // i ten powinno znaleźć
        Topic t5 = getTopic(sup2, "title qwerty");
        Topic t6 = getTopic(sup2, "title qwerty");

        TopicListDto dto = topicService.findByKeywordForSupervisor(0, 5, "wer", "sup1");
        assertNotNull(dto.getTopics());
        assertEquals(2, dto.getTopics().size());
        assertTopicDtoEqualsTopic(t1, dto.getTopics().get(0));
        assertTopicDtoEqualsTopic(t4, dto.getTopics().get(1));
    }

    @Test
    public void findByKeywordForStudentsTest() {
        User sup = getSupervisor("sup");
        User stud1 = getStudent("stud1");
        User stud2 = getStudent("stud2");
        Topic t1 = getTopic(sup, null, "title aaa", false);
        Topic t2 = getTopic(sup, null, "title abc title", true);   // powinno znaleźć ten,
        Topic t3 = getTopic(sup, stud1, "title abc t", true);
        Topic t4 = getTopic(sup, stud2, "title abc", false);
        Topic t5 = getTopic(sup, null, "title qwerty", true);
        Topic t6 = getTopic(sup, null, "title abc abc", false);
        Topic t7 = getTopic(sup, null, "abc title", true);         // i ten

        TopicListDto dto = topicService.findByKeywordForStudents(0, 5, "abc");
        assertNotNull(dto.getTopics());
        assertEquals(2, dto.getTopics().size());
        assertTopicDtoEqualsTopic(t7, dto.getTopics().get(0));
        assertTopicDtoEqualsTopic(t2, dto.getTopics().get(1));
    }

    @Test
    public void saveAsSupervisorTest() {
        User sup = getSupervisor("sup");
        TopicDto dto = new TopicDto("title", "desc", 0, "", 0, "", false);

        // fail: nie ma takie supervisora
        assertFalse( topicService.saveAsSupervisor(dto, "sup_that_doesnt_exist") );

        // fail: nie ma takiego studenta
        User stud = getStudent("stud");
        dto.setStudentId(stud.getId() + 1);
        assertFalse( topicService.saveAsSupervisor(dto, "sup") );

        // success: bez studenta
        dto.setStudentId(0);
        assertTrue(topicService.saveAsSupervisor(dto, "sup"));

        // success: ze studentem
        dto.setStudentId(stud.getId());
        assertTrue(topicService.saveAsSupervisor(dto, "sup"));
    }

    @Test
    public void updateAsSupervisorTest() {
        User sup = getSupervisor("sup");
        User stud = getStudent("stud");
        Topic t1 = getTopic(sup, stud, false);
        Topic t2 = getTopic(sup, stud, true);
        Topic t3 = getTopic(sup, null, false);
        TopicDto dto = new TopicDto("title", "desc", sup.getId(), "", stud.getId(), "", false);
        dto.setId(t1.getId());

        // fail: nie ma takie supervisora
        assertFalse(topicService.updateBySupervisor(dto, "sup_that_doesnt_exist"));

        // fail: nie ma takiego studenta
        dto.setStudentId(stud.getId() + 1);
        assertFalse(topicService.updateBySupervisor(dto, "sup"));

        // fail: zatwierdzony
        dto.setStudentId(stud.getId());
        dto.setId(t2.getId());
        assertFalse(topicService.updateBySupervisor(dto, "sup"));

        // success: dodanie studenta
        dto.setId(t3.getId());
        assertTrue(topicService.updateBySupervisor(dto, "sup"));
        Topic t3_2 = topicsRepository.findOne(t3.getId());
        assertEquals(stud.getId(), t3_2.getStudentId());

        // sukces: zmiana tytułu i opisu
        dto.setId(t1.getId());
        dto.setDescription("new desc");
        dto.setTitle("new title");
        assertTrue(topicService.updateBySupervisor(dto, "sup"));
        Topic t1_2 = topicsRepository.findOne(t1.getId());
        assertEquals("new title", t1_2.getTitle());
        assertEquals("new desc", t1_2.getDescription());
    }

    @Test
    public void deleteAsSupervisorTest() {
        User sup = getSupervisor("sup");
        User anotherSup = getSupervisor("another supervisor");
        Topic t1 = getTopic(sup, null, false);
        Topic t2 = getTopic(sup, null, true);               // tego nie skasuje bo jest zatwierdzony
        Topic t3 = getTopic(anotherSup, null, false);       // tego nie skasuje bo przypisany do innego promotora

        // success
        assertTrue(topicService.deleteBySupervisor(t1.getId(), "sup"));
        assertNull(topicsRepository.findOne(t1.getId()));

        // fail: zatwierdzony
        assertFalse(topicService.deleteBySupervisor(t2.getId(), "sup"));
        assertNotNull(topicsRepository.findOne(t2.getId()));

        // fail: nie ten promotor
        assertFalse(topicService.deleteBySupervisor(t3.getId(), "sup"));
        assertNotNull(topicsRepository.findOne(t3.getId()));
    }
}
