package elkadyplom.dto;


public class TopicDto {

    private String title;
    private String description;
    private int supervisor_id;
    private int student_id;

    public TopicDto() {
    }

    public TopicDto(String title, String description, int supervisor_id) {
        this(title, description, supervisor_id, 0);
    }

    public TopicDto(String title, String description, int supervisor_id, int student_id) {
        this.title = title;
        this.description = description;
        this.supervisor_id = supervisor_id;
        this.student_id = student_id;
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

    public int getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(int supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
