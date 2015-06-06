package elkadyplom.dto;


import elkadyplom.model.User;

/**
 * DTO przechowujące podstawowe dane użytkownika.
 */

public class BasicUserDto {

    /**
     * Identyfikator użytkownika w bazie.
     */
    private int id;

    /**
     * Imię i nazwisko użytkownika.
     */
    private String name;

    public BasicUserDto() {
        // enable default;
    }

    public BasicUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public BasicUserDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
