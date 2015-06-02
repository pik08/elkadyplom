package elkadyplom.repository;

import elkadyplom.model.Declaration;
import elkadyplom.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeclarationRepository extends CrudRepository<Declaration, Integer> {

    public List<Declaration> findByStudent(User student);
}
