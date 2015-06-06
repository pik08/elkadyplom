package elkadyplom.dto;


import elkadyplom.model.ThesisType;
import elkadyplom.model.Topic;

/**
 * DTO trzymające dane tematu pracy dyplomowej.
 */

public class TopicDto {

    /**
     * Identyfikator tematu w bazie.
     */
    private int id;

    /**
     * Tytuł.
     */
    private String title;

    /**
     * Opis.
     */
    private String description;

    /**
     * Identyfikator promotora.
     */
    private int supervisorId;

    /**
     * Identyfikator studenta, przypisanego do tematu.
     */
    private int studentId;

    /**
     * Imię i nazwisko promotra tematu.
     */
    private String supervisorName;

    /**
     * Imię i nazwisko studenta, przypisanego do tematu.
     */
    private String studentName;

    /**
     * Czy temat jest powierdzony przez administratora.
     */
    private boolean confirmed;

    /**
     * Typ pracy dyplomowej.
     */
    private ThesisType thesisType;

    public TopicDto() {
        // default constructor enabled
    }

    public TopicDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.description = topic.getDescription();
        this.supervisorId = topic.getSupervisorId();
        this.studentId = topic.getStudentId();
        this.supervisorName = topic.getSupervisorName();
        this.studentName = topic.getStudentName();
        this.confirmed = topic.isConfirmed();
        this.thesisType = topic.getThesisType();
    }

    public TopicDto(String title, String description, int supervisorId, String supervisorName, boolean confirmed) {
        this(title, description, supervisorId, supervisorName, 0, "", confirmed);
    }

    public TopicDto(String title, String description, int supervisorId, String supersorName, int studentId, String studentName, boolean confirmed) {
        this.title = title;
        this.description = description;
        this.supervisorId = supervisorId;
        this.supervisorName = supersorName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.confirmed = confirmed;
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

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
}
