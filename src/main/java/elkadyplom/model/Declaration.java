package elkadyplom.model;

import javax.persistence.*;

/**
 * Encja reprezentująca deklarację studenta, że chciałby on realizować dany temat w ramach swojej pracy dyplomowej.
 */

@Entity
@Table(name = "declarations")
public class Declaration {

    @Id
    @GeneratedValue
    private int id;

    /**
     * Waga, jaką student przypisuje tematowi. Im większa, tym mniej istotny temat ("pierwszy wybór" a "trzeci wybór").
     */
    @Column( nullable = false )
    private int rank;

    /**
     * Student, który składa tę deklarację.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private User student;

    /**
     * Deklarowany temat.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Declaration() {
        // enable default
    }

    public Declaration(User student, Topic topic, int rank) {
        this.rank = rank;
        this.student = student;
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getTopicId() {
        if (topic == null)
            return 0;

        return topic.getId();
    }

    public String getTopicTitle() {
        if (topic == null)
            return "";

        return topic.getTitle();
    }

    public String getTopicSupervisorName() {
        if (topic == null)
            return "";

        return topic.getSupervisorName();
    }

}
