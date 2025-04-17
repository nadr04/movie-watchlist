package lt.projectx.moviewatchlist.repository;

import lt.projectx.moviewatchlist.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    //    List<Movie> findAllByWatcherId(String watcherId);

    // Find movies by genre
//    List<Movie> findAllByGenre(String genre);
//
//    // Find movies by director
//    List<Movie> findAllByDirector(String director);
//
//    // Find by partial title match (case-insensitive)
//    List<Movie> findByTitleContainingIgnoreCase(String title);
//
//    // Get all movies released after a given year
//    List<Movie> findByReleaseYearGreaterThan(Integer year);

}
