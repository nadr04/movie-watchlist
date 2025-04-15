package lt.projectx.moviewatchlist.service;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.entity.Movie;
import lt.projectx.moviewatchlist.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        return movieRepository.saveAndFlush(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found"));
    }

    public Movie patchMovieById(Integer id, Movie movieFromRequest) {
        Movie movieFromDb = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found"));

        if (StringUtils.isNotBlank(movieFromRequest.getTitle()) &&
                !movieFromRequest.getTitle().equals(movieFromDb.getTitle())) {
            movieFromDb.setTitle(movieFromRequest.getTitle());
        }
        if (StringUtils.isNotBlank(movieFromRequest.getGenre()) &&
                !movieFromRequest.getGenre().equals(movieFromDb.getGenre())) {
            movieFromDb.setGenre(movieFromRequest.getGenre());
        }
        if (StringUtils.isNotBlank(movieFromRequest.getDirector()) &&
                !movieFromRequest.getDirector().equals(movieFromDb.getDirector())) {
            movieFromDb.setDirector(movieFromRequest.getDirector());
        }
        if (movieFromRequest.getReleaseYear() != null &&
                movieFromRequest.getReleaseYear() > 1800 &&
                !movieFromRequest.getReleaseYear().equals(movieFromDb.getReleaseYear())) {
            movieFromDb.setReleaseYear(movieFromRequest.getReleaseYear());
        }
        if (movieFromRequest.getRuntimeMinutes() != null &&
                movieFromRequest.getRuntimeMinutes() > 0 &&
                !movieFromRequest.getRuntimeMinutes().equals(movieFromDb.getRuntimeMinutes())) {
            movieFromDb.setRuntimeMinutes(movieFromRequest.getRuntimeMinutes());
        }


        return movieRepository.saveAndFlush(movieFromDb);
    }

}