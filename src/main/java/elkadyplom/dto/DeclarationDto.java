package elkadyplom.dto;

import elkadyplom.model.Declaration;

public class DeclarationDto {

    private int declarationId;
    private int rank;
    private int topicId;
    private String topicTitle;
    private String topicSupervisorName;

    public DeclarationDto() {
    }

    public DeclarationDto(Declaration d) {
        declarationId = d.getId();
        rank = d.getRank();
        topicId = d.getTopicId();
        topicTitle = d.getTopicTitle();
        topicSupervisorName = d.getTopicSupervisorName();
    }

    public DeclarationDto(int declarationId, int rank, int topicId, String topicTitle, String topicSupervisorName) {
        this.declarationId = declarationId;
        this.rank = rank;
        this.topicId = topicId;
        this.topicTitle = topicTitle;
        this.topicSupervisorName = topicSupervisorName;
    }

    public int getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(int declarationId) {
        this.declarationId = declarationId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicSupervisorName() {
        return topicSupervisorName;
    }

    public void setTopicSupervisorName(String topicSupervisorName) {
        this.topicSupervisorName = topicSupervisorName;
    }
}
