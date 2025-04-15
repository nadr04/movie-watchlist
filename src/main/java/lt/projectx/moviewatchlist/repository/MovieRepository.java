package lt.projectx.moviewatchlist.repository;

import lt.projectx.moviewatchlist.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
//    List<Movie> findAllByWatcherId(Integer watcherId);

}
