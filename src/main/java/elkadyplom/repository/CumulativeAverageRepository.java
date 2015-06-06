package elkadyplom.repository;

import elkadyplom.model.CumulativeAverage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CumulativeAverageRepository extends PagingAndSortingRepository<CumulativeAverage, Integer> {
}
