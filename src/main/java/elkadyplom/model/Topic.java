package elkadyplom.model;


import javax.persistence.*;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    @Column(length = 500)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

    @ManyToOne(optional = true)
    @JoinColumn(name = "student_id")
    private User student;

    private boolean confirmed = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getSupervisorId() {
        if (supervisor == null) return 0;
        return supervisor.getId();
    }

    public int getStudentId() {
        if (student == null) return 0;
        return student.getId();
    }

    public String getSupervisorName() {
        if (supervisor == null) return "";
        return supervisor.getName();
    }

    public String getStudentName() {
        if (student == null) return "";
        return student.getName();
    }
}
