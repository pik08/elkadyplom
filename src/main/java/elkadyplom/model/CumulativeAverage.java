package elkadyplom.model;

import javax.persistence.*;

/**
 * Encja reprezentująca średnią skumulowaną studenta.
 */

@Entity
@Table( name = "cumulative_averages" )
public class CumulativeAverage {

    /**
     * Identyfikator.
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * Średnia skumulowana.
     */
    private double average;

    /**
     * Student, którego średnia to jest.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", unique = true)
    private User student;

    public CumulativeAverage() {
        // enable default
    }

    public CumulativeAverage(double average, User student) {
        this.average = average;
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public int getStudentId() {
        if (student == null)
            return 0;

        return student.getId();
    }

    public String getStudentName() {
        if (student == null)
            return "";

        return student.getName();
    }
}
