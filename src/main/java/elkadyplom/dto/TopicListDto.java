package elkadyplom.dto;

import elkadyplom.model.Topic;
import elkadyplom.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Dto przechowujące listę tematów do wyświetlenia na stronie plus kilka innych parametrów - do wyświetlenia
 * w widoku listy tematów.
 */

public class TopicListDto {

    /**
     * Liczba stron.
     */
    private int pagesCount;

    /**
     * Liczba tematów.
     */
    private long totalTopics;

    /**
     * Wiadomość o działaniu.
     */
    private String actionMessage;

    /**
     * Wiadomość o wyszukiwaniu.
     */
    private String searchMessage;

    /**
     * Lista dto z danymi tematów.
     */
    private List<TopicDto> topics;

    /**
     * Lista danych promotorów.
     */
    private List<BasicUserDto> supervisors;

    /**
     * Lista danych studentów.
     */
    private List<BasicUserDto> students;

    public TopicListDto() {
    }

    public TopicListDto(int pages, long totalTopics, List<Topic> topics) {
        this(pages, totalTopics, topics, null, null);
    }

    public TopicListDto(int pages, long totalTopics, List<Topic> topics, List<User> supervisors, List<User> students) {
        this.pagesCount = pages;
        this.totalTopics = totalTopics;

        this.topics = new ArrayList<TopicDto>();
        if (topics != null) {
            for (Topic t : topics) {
                this.topics.add(new TopicDto(t));
            }
        }

        this.supervisors = new ArrayList<BasicUserDto>();
        if (supervisors != null) {
            for (User supervisor : supervisors) {
                this.supervisors.add(new BasicUserDto(supervisor));
            }
        }

        this.students = new ArrayList<BasicUserDto>();
        if (students != null) {
            for (User student : students) {
                this.students.add(new BasicUserDto(student));
            }
        }
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<TopicDto> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDto> topics) {
        this.topics = topics;
    }

    public long getTotalTopics() {
        return totalTopics;
    }

    public void setTotalTopics(long totalTopics) {
        this.totalTopics = totalTopics;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    public List<BasicUserDto> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<BasicUserDto> supervisors) {
        this.supervisors = supervisors;
    }

    public List<BasicUserDto> getStudents() {
        return students;
    }

    public void setStudents(List<BasicUserDto> students) {
        this.students = students;
    }
}