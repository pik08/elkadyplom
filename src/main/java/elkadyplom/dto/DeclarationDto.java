package elkadyplom.dto;

import elkadyplom.model.Declaration;

/**
 * DTO przechowujące dane dot. deklaracji studenta.
 */

public class DeclarationDto {

    /**
     * Id deklaracji, jeśli jest już ona w bazie.
     */
    private int declarationId;

    /**
     * Waga deklaracji.
     */
    private int rank;

    /**
     * Id deklarowanego tematu.
     */
    private int topicId;

    /**
     * Tytuł deklarowanego tematu.
     */
    private String topicTitle;

    /**
     * Imię i nazwisko promotora danego tematu.
     */
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
