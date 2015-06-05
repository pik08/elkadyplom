package elkadyplom.dto;

import elkadyplom.model.Topic;
import elkadyplom.model.User;

import java.util.ArrayList;
import java.util.List;

public class TopicListDto {
    private int pagesCount;
    private long totalTopics;

    private String actionMessage;
    private String searchMessage;

    private List<TopicDto> topics;

    private List<BasicUserDto> supervisors;
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