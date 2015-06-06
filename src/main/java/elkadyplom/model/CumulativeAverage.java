package elkadyplom.model;

import javax.persistence.*;

@Entity
@Table( name = "cumulative_averages" )
public class CumulativeAverage {

    @Id
    @GeneratedValue
    private int id;

    private double average;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", unique = true)
    private User student;

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
}
