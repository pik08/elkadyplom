package elkadyplom.repository;

import org.springframework.data.repository.CrudRepository;
import elkadyplom.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
