package elkadyplom.dto;

import elkadyplom.model.CumulativeAverage;

import java.util.List;


/**
 * DTO z danymi dotyczącymi przypisania tematu do studenta.
 */

public class AssignmentDto {

    /**
     * Id studenta w bazie.
     */
    private int studentId;

    /**
     * Imię i nazwisko studenta.
     */
    private String studentName;

    /**
     * Średnia skumulowana studenta.
     */
    private double cumulativeAverage;

    /**
     * Lista zadeklarowanych przez studenta tematów
     * (maksymalnie jeden z nich powinien mieć zaznaczone, że został studentowi przuporządkowany).
     */
    List<DeclaredTopicDto> topics;

    public AssignmentDto() {
    }

    public AssignmentDto(CumulativeAverage avg, List<DeclaredTopicDto> topics) {
        this.studentId = avg.getStudentId();
        this.studentName = avg.getStudentName();
        this.cumulativeAverage = avg.getAverage();
        this.topics = topics;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getCumulativeAverage() {
        return cumulativeAverage;
    }

    public void setCumulativeAverage(double cumulativeAverage) {
        this.cumulativeAverage = cumulativeAverage;
    }

    public List<DeclaredTopicDto> getTopics() {
        return topics;
    }

    public void setTopics(List<DeclaredTopicDto> topics) {
        this.topics = topics;
    }
}
