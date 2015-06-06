package elkadyplom;

import elkadyplom.dto.AssignmentDto;
import elkadyplom.dto.DeclaredTopicDto;
import elkadyplom.model.CumulativeAverage;
import elkadyplom.model.Declaration;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import elkadyplom.repository.CumulativeAverageRepository;
import elkadyplom.repository.DeclarationRepository;
import elkadyplom.service.DeclarationService;
import org.junit.Assert;
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
public class DeclarationServiceTest extends AbstractTestBase {

    @Test
    public void assignTopicTest() throws Exception {
        // stwórz promotorów
        User sup1 = getSupervisor("sup1");
        User sup2 = getSupervisor("sup2");
        User sup3 = getSupervisor("sup3");

        // stwórz śtudentów wraz ze średnimi: kolejność po średnich to stud1, stud3, stud4, stud2
        User stud1 = getStudent("stud1");
        User stud2 = getStudent("stud2");
        User stud3 = getStudent("stud3");
        User stud4 = getStudent("stud4");
        CumulativeAverage avg1 = getCumulativeAverage(stud1, 4.34);
        CumulativeAverage avg2 = getCumulativeAverage(stud2, 3.23);
        CumulativeAverage avg3 = getCumulativeAverage(stud3, 4.21);
        CumulativeAverage avg4 = getCumulativeAverage(stud4, 3.87);

        // stwórz tematy promotorów, bez przypisanych studentów
        Topic sup1Topic1 = getTopic(sup1);
        Topic sup1Topic2 = getTopic(sup1);
        Topic sup1Topic3 = getTopic(sup1);
        Topic sup1Topic4 = getTopic(sup1);
        Topic sup2Topic1 = getTopic(sup2);
        Topic sup2Topic2 = getTopic(sup2);
        Topic sup2Topic3 = getTopic(sup2);
        Topic sup3Topic1 = getTopic(sup3);
        Topic sup3Topic2 = getTopic(sup3);
        Topic sup3Topic3 = getTopic(sup3);

        // utwórz tematy już przypisane do studentów (ich też utwórz)
        // ani te tematy, ani ci studenci, nie powinni być brani pod uwagę przy przyporządkowywaniu tematów
        User stud5 = getStudent("stud5");
        User stud6 = getStudent("stud6");
        User stud7 = getStudent("stud7");
        CumulativeAverage avg5 = getCumulativeAverage(stud5, 4.41);
        CumulativeAverage avg6 = getCumulativeAverage(stud6, 4.02);
        CumulativeAverage avg7 = getCumulativeAverage(stud7, 3.43);
        Topic assignedSup1Topic = getTopic(sup1, stud6);
        Topic assignedSup2Topic = getTopic(sup2, stud7);
        Topic assignedSup3Topic = getTopic(sup3, stud5);

        // deklaracje stud4
        Declaration stud4dec1 = getDeclaration(stud4, sup3Topic1, 1);
        Declaration stud4dec2 = getDeclaration(stud4, sup3Topic3, 3);
        Declaration stud4dec3 = getDeclaration(stud4, sup1Topic1, 2); // dostanie ten, drugi wybór,bo 1. wybór zajęty przez stud3
        Declaration stud4dec4 = getDeclaration(stud4, sup1Topic4, 4);

        // deklaracje stud1
        Declaration stud1dec1 = getDeclaration(stud1, sup1Topic4, 2);
        Declaration stud1dec2 = getDeclaration(stud1, sup1Topic2, 1);   // ten dostanie: ma najwyższą średnią
        Declaration stud1dec3 = getDeclaration(stud1, sup2Topic1, 3);

        // deklaracje stud3
        Declaration stud3dec1 = getDeclaration(stud3, sup3Topic1, 1); // dostanie swój pierwszy wybór
        Declaration stud3dec2 = getDeclaration(stud3, sup3Topic3, 2);
        Declaration stud3dec3 = getDeclaration(stud3, sup2Topic2, 3);

        // deklaracje stud2
        Declaration stud2dec1 = getDeclaration(stud2, sup1Topic2, 1);
        Declaration stud2dec2 = getDeclaration(stud2, sup1Topic1, 3);
        Declaration stud2dec3 = getDeclaration(stud2, sup2Topic3, 4); // dostanie ten (swój ostatni wybór) bo pozostałe
        Declaration stud2dec4 = getDeclaration(stud2, sup3Topic1, 2); // wybrane przez innych a on ma najnizsżą średnią

        // przydziel tematy
        List<AssignmentDto> result = declarationService.assignTopics();
        assertNotNull(result);
        assertEquals(4, result.size());

        // pierwszy powinien być stud1
        AssignmentDto dto = result.get(0);
        assertAssigmentDto(dto, stud1, avg1.getAverage(), 3);
        assertDeclaredTopicDto(dto.getTopics().get(0), sup1Topic2, true);
        assertDeclaredTopicDto(dto.getTopics().get(1), sup1Topic4, false);
        assertDeclaredTopicDto(dto.getTopics().get(2), sup2Topic1, false);

        // drugi powinien być stud3
        dto = result.get(1);
        assertAssigmentDto(dto, stud3, avg3.getAverage(), 3);
        assertDeclaredTopicDto(dto.getTopics().get(0), sup3Topic1, true);
        assertDeclaredTopicDto(dto.getTopics().get(1), sup3Topic3, false);
        assertDeclaredTopicDto(dto.getTopics().get(2), sup2Topic2, false);

        // trzeci powinien być stud4
        dto = result.get(2);
        assertAssigmentDto(dto, stud4, avg4.getAverage(), 4);
        assertDeclaredTopicDto(dto.getTopics().get(0), sup3Topic1, false);
        assertDeclaredTopicDto(dto.getTopics().get(1), sup1Topic1, true);
        assertDeclaredTopicDto(dto.getTopics().get(2), sup3Topic3, false);
        assertDeclaredTopicDto(dto.getTopics().get(3), sup1Topic4, false);

        // czwarty powinien być stud2
        dto = result.get(3);
        assertAssigmentDto(dto, stud2, avg2.getAverage(), 4);
        assertDeclaredTopicDto(dto.getTopics().get(0), sup1Topic2, false);
        assertDeclaredTopicDto(dto.getTopics().get(1), sup3Topic1, false);
        assertDeclaredTopicDto(dto.getTopics().get(2), sup1Topic1, false);
        assertDeclaredTopicDto(dto.getTopics().get(3), sup2Topic3, true);

    }

    private void assertAssigmentDto(AssignmentDto dto, User stud, double average, int topicsNumber) {
        assertNotNull(dto);
        assertEquals(stud.getId(), dto.getStudentId());
        assertEquals(stud.getName(), dto.getStudentName());
        assertEquals(average, dto.getCumulativeAverage(), 0);
        assertNotNull(dto.getTopics());
        assertEquals(topicsNumber, dto.getTopics().size());
    }

    private void assertDeclaredTopicDto(DeclaredTopicDto t, Topic expectedTopic, boolean expectedAssigned) {
        assertNotNull(t);
        assertEquals(expectedTopic.getId(), t.getTopicId());
        assertEquals(expectedAssigned, t.isAssigned());
    }

    private Declaration getDeclaration(User student, Topic topic, int rank){
        Declaration declaration = new Declaration(student, topic, rank);
        return declarationRepository.save(declaration);
    }

    private CumulativeAverage getCumulativeAverage(User student, double average) {
        CumulativeAverage avg = new CumulativeAverage(average, student);
        return cumulativeAverageRepository.save(avg);
    }
}
