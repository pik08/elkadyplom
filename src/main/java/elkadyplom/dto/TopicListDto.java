package elkadyplom.dto;

import elkadyplom.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicListDto {
    private int pagesCount;
    private long totalTopics;

    private String actionMessage;
    private String searchMessage;

    private List<TopicDto> topics;

    public TopicListDto() {
    }

    public TopicListDto(int pages, long totalTopics, List<Topic> topics) {
        this.pagesCount = pages;
        this.totalTopics = totalTopics;

        this.topics = new ArrayList<TopicDto>();
        for (Topic t : topics ) {
            this.topics.add(new TopicDto(t));
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
}