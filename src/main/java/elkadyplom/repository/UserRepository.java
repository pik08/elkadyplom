package elkadyplom.repository;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import elkadyplom.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    public final static String GET_SUPERVISOR_LIST = "select u from Users u where u.role = :role";

    User findByEmail(String email);

    @Query()
    List<User> getUserListByRole(@Param("role") Role role);
}
