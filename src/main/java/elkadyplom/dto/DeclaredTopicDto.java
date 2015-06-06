package elkadyplom.dto;


import elkadyplom.model.Topic;

/**
 * DTO trzymające dane zadeklarowanego tematu.
 */

public class DeclaredTopicDto {

    /**
     * Id tematu w bazie.
     */
    private int topicId;

    /**
     * Imię i nazwisko promotora proponującego temat.
     */
    private String supervisorName;

    /**
     * Tytuł tematu.
     */
    private String title;

    /**
     * Czy temat został przyporządkowany.
     */
    private boolean assigned = false;

    public DeclaredTopicDto() {
    }

    public DeclaredTopicDto(Topic topic) {
        this.topicId = topic.getId();
        this.supervisorName = topic.getSupervisorName();
        this.title = topic.getTitle();
    }

    public DeclaredTopicDto(Topic topic, boolean assigned) {
        this.topicId = topic.getId();
        this.supervisorName = topic.getSupervisorName();
        this.title = topic.getTitle();
        this.assigned = assigned;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
}
