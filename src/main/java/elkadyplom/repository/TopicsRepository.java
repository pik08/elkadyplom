package elkadyplom.repository;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicsRepository extends PagingAndSortingRepository<Topic, Integer> {

    //public final static String FIND_BY_KEYWORD_QUERY = "select t from Topic t where t.title like %:keyword%";

//    @Query(FIND_BY_KEYWORD_QUERY)
//    public Page<Topic> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    public Page<Topic> findByTitleLike(Pageable pageable, String title);

}