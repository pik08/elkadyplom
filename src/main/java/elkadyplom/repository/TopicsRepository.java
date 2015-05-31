package elkadyplom.repository;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicsRepository extends PagingAndSortingRepository<Topic, Integer> {

    public final static String FIND_BY_KEYWORD_SUPERVISOR_QUERY =
            "select t from Topic t where t.title like :keyword and t.supervisor = :supervisor";

    @Query(FIND_BY_KEYWORD_SUPERVISOR_QUERY)
    public Page<Topic> findByTitleAndSupervisor(@Param("keyword") String keyword, @Param("supervisor") User supervisor, Pageable pageable );

    public Page<Topic> findByTitleLike(Pageable pageable, String title);

    public Page<Topic> findBySupervisor(Pageable pageable, User supervisor);

}