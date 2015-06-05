package elkadyplom.repository;

import elkadyplom.model.Declaration;
import elkadyplom.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeclarationRepository extends CrudRepository<Declaration, Integer> {

    public final static String DELETE_BY_STUDENT_QUERY = "delete from Declaration d where d.student = :student";

    public List<Declaration> findByStudent(User student);

    @Modifying
    @Query(DELETE_BY_STUDENT_QUERY)
    public int deleteByStudent(@Param("student") User student);
}
