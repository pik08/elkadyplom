package elkadyplom.repository;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Warstwa abstrakcji danych zajmujaca się tematami prac dyplomowych.
 */

public interface TopicsRepository extends PagingAndSortingRepository<Topic, Integer> {

    /**
     * Zapytanie wyszukujące tematy danego promotora, pasujące do danego słowa kluczowego.
     */
    public final static String FIND_BY_KEYWORD_SUPERVISOR_QUERY =
            "select t from Topic t where t.title like :keyword and t.supervisor = :supervisor";

    /**
     * Zapytanie wyszukujące potwierdzone tematy, przypisane do danego studenta.
     */
    public final static String FIND_ASSIGNED_CONFIRMED_TOPICS =
            "select t from Topic t where t.student is not null and t.confirmed = true";

    /**
     * Zapytanie wyszukujące tematy zatwierdzone i nieprzypisane do żadnego studenta.
     */
    public final static String FIND_NOT_ASSIGNED_CONFIRMED =
            "select t from Topic t where t.student is null and t.confirmed = true";

    /**
     * Zapytanie wyszukujące zatwierdzone tematy, nieprzypisane do żadnego studenta, o tytule pasującym do danego słowa kluczowego.
     */
    public final static String FIND_NOT_ASSIGNED_CONFIRMED_LIKE =
            "select t from Topic t where t.student is null and t.confirmed = true and t.title like :keyword";

    /**
     * Metoda wyszukująca listę tematów danego promotora, pasujących do danego słowa kluczowego.
     * @param keyword słowo kluczowe
     * @param supervisor promotor
     * @param pageable parametr stronicowania
     * @return strona tematów
     */
    @Query(FIND_BY_KEYWORD_SUPERVISOR_QUERY)
    public Page<Topic> findByTitleAndSupervisor(@Param("keyword") String keyword, @Param("supervisor") User supervisor, Pageable pageable );

    /**
     * Metoda wyszukująca listę tematów pasujących do danego słowa kluczowego.
     * @param pageable parametr stronicowania
     * @return strona tematów
     */
    public Page<Topic> findByTitleLike(Pageable pageable, String title);

    /**
     * Metoda wyszukująca listę tematów danego promotora.
     * @param supervisor promotor
     * @param pageable parametr stronicowania
     * @return strona tematów
     */
    public Page<Topic> findBySupervisor(Pageable pageable, User supervisor);

    /**
     * Metoda wyszukująca listę tematów przypisanych do danego studenta.
     * @param pageable parametr stronicowania
     * @param student student
     * @return strona tematów
     */
    public Page<Topic> findByStudent(Pageable pageable, User student);

    /**
     * Metoda wyszukująca zatwierdzone tematy, przypisane do danego studenta.
     * @return lista tematów
     */
    @Query(FIND_ASSIGNED_CONFIRMED_TOPICS)
    List<Topic> findAssignedConfirmedTopics();

    /**
     * Metoda wyszukująca zatwierdzone tematy, nieprzypisane do żadnego studenta, o tytule pasującym do danego słowa kluczowego.
     * @param keyword słowo kluczowe
     * @param pageable parametr stronicowania
     * @return strona tematów
     */
    @Query(FIND_NOT_ASSIGNED_CONFIRMED_LIKE)
    Page<Topic> findConfirmedNotAssignedByTitleLike(@Param("keyword") String keyword, Pageable pageable);

    /**
     * Metoda wyszukująca tematy zatwierdzone i nieprzypisane do żadnego studenta.
     * @param pageable parametr stronicowania
     * @return strona tematów
     */
    @Query(FIND_NOT_ASSIGNED_CONFIRMED)
    Page<Topic> findAllConfirmedNotAssigned(Pageable pageable);
}