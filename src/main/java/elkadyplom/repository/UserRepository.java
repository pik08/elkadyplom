package elkadyplom.repository;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import elkadyplom.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Warstwa abstrakcji danych, zajmująca się użytkownikami.
 */

public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Zapytanie wyszukujące użytkowników o danej roli.
     */
    public final static String GET_USERS_BY_ROLE = "select u from User u where u.role = :role";

    /**
     * Metoda wyszukująca użytkownika po adresie email.
     * @param email adres email
     * @return użytkownik
     */
    User findByEmail(String email);

    /**
     * Metoda wyszukująca uzytkowników o danej roli.
     * @param role rola
     * @return lista użytkowników
     */
    @Query(GET_USERS_BY_ROLE)
    List<User> getUserListByRole(@Param("role") Role role);
}
