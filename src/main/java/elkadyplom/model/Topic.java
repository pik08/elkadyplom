package elkadyplom.model;


import elkadyplom.dto.TopicDto;

import javax.persistence.*;

/**
 * Encja reprezentująca temat pracy dyplomowej. Temat musi być przypisany do promotora i może mieć przypisanego studenta (jest to wtedy
 * temat prywatny lub temat został już przyporządkowany na podstawie deklaracji studenta).
 */

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue
    private int id;

    /**
     * Tytuł.
     */
    private String title;

    /**
     * Opis.
     */
    @Column(length = 500)
    private String description;

    /**
     * Promotor.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

    /**
     * Przypisany student. Argument opcjonalny.
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "student_id")
    private User student;

    /**
     * Typ pracy dyplomowej (inżynierska/magisterska).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "thesis_type")
    private ThesisType thesisType;

    /**
     * Czy temat został zatwierdzony przez administratora.
     */
    private boolean confirmed = false;


    public Topic() {
        // enable default
    }

    /**
     * Konstruktor.
     * @param topicDto dto z danymi tematu
     * @param supervisor promotor
     * @param student student
     */
    public Topic(TopicDto topicDto, User supervisor, User student) {
        this.setAll(topicDto, supervisor, student);
    }

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

    public ThesisType getThesisType() {
        return thesisType;
    }

    public void setThesisType(ThesisType thesisType) {
        this.thesisType = thesisType;
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

    /**
     * Metoda ustawiająca wszystkie parametry tematu.
     * @param topicDto dto z danymi tematu
     * @param supervisor promotor
     * @param student student
     */
    public void setAll(TopicDto topicDto, User supervisor, User student) {
        this.confirmed = topicDto.isConfirmed();
        this.description = topicDto.getDescription();
        this.title = topicDto.getTitle();
        this.thesisType = topicDto.getThesisType();

        if (supervisor != null)
            this.supervisor = supervisor;
        if (student != null)
            this.student = student;
    }
}
