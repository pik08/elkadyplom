package elkadyplom.repository;

import elkadyplom.model.CumulativeAverage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Warstwa abstrakcji danych, zajmująca się encją średniej skumulowanej.
 */

public interface CumulativeAverageRepository extends PagingAndSortingRepository<CumulativeAverage, Integer> {
}
