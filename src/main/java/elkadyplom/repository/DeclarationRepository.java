package elkadyplom.repository;

import elkadyplom.model.Declaration;
import elkadyplom.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Warstwa abstrakcji danych, zajmuje się deklaracjami studentów o wyborze tematów pracy dyplomowej.
 */

public interface DeclarationRepository extends CrudRepository<Declaration, Integer> {

    /**
     * Zapytanie usuwające deklaracje przypisane do danego stuenta.
     */
    public final static String DELETE_BY_STUDENT_QUERY = "delete from Declaration d where d.student = :student";

    /**
     * Metoda wyszukująca deklaracje przypisane do danego studenta.
     * @param student student
     * @param sort porzadek sortowania
     * @return posortowana lista deklaracji
     */
    public List<Declaration> findByStudent(User student, Sort sort);

    /**
     * Metoda usuwająca deklaracje przypisane do danego studenta.
     * @param student student
     * @return liczba usuniętych rekordów
     */
    @Modifying
    @Query(DELETE_BY_STUDENT_QUERY)
    public int deleteByStudent(@Param("student") User student);
}
