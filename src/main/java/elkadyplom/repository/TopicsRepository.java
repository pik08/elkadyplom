package elkadyplom.repository;

import elkadyplom.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TopicsRepository extends PagingAndSortingRepository<Topic, Integer> {
    //Page<Topic> findByNameLike(Pageable pageable, String name);
}