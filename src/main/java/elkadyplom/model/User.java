package elkadyplom.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Encja reprezentująca użytkownika systemu. Są trzy rodzaje użytkownikow: student, promotor i administrator.
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;

    /**
     * Adres email uzytkownika, bedący jego loginem.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Imię i nazwisko.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Czy uzytkownik jest niezablokowany w systemie.
     */
    @Column(nullable = false)
    private String enabled;

    /**
     * Hasło.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rola użytkownika.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {
        return Role.ROLE_ADMIN.equals(role);
    }

    public boolean isSupervisor() {
        return Role.ROLE_SUPERVISOR.equals(role);
    }

    public boolean isStudent() {
        return Role.ROLE_STUDENT.equals(role);
    }
}
