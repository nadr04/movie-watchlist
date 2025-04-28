package lt.projectx.moviewatchlist.service;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.converter.MovieConverter;
import lt.projectx.moviewatchlist.dto.CreateMovieRequest;
import lt.projectx.moviewatchlist.entity.Movie;
import lt.projectx.moviewatchlist.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private String generateMovieId() {
        String prefix = "MOV";
        int totalLength = 10;
        int currentCount = movieRepository.findAll().size() + 1;
        String numberPart = String.format("%0" + (totalLength - prefix.length()) + "d", currentCount);
        return prefix + numberPart;
    }

    public Movie addMovie(CreateMovieRequest request) {
        Movie movie = MovieConverter.toEntity(request);
        movie.setId(generateMovieId());
        return movieRepository.save(movie);
    }

    public List<Movie> filterMovies(String title, String genre, String director, Integer releaseYear) {
        return movieRepository.findAll().stream()
                .filter(m -> title == null || m.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(m -> genre == null || m.getGenre().equalsIgnoreCase(genre))
                .filter(m -> director == null || m.getDirector().equalsIgnoreCase(director))
                .filter(m -> releaseYear == null || m.getReleaseYear().equals(releaseYear))
                .toList();
    }

    public Movie findMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found"));
    }

    public Movie patchMovieById(String id, Movie movieFromRequest) {
        Movie movieFromDb = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found"));

        if (StringUtils.isNotBlank(movieFromRequest.getTitle()) && !movieFromRequest.getTitle().equals(movieFromDb.getTitle())) {
            movieFromDb.setTitle(movieFromRequest.getTitle());
        }
        if (StringUtils.isNotBlank(movieFromRequest.getGenre()) && !movieFromRequest.getGenre().equals(movieFromDb.getGenre())) {
            movieFromDb.setGenre(movieFromRequest.getGenre());
        }
        if (StringUtils.isNotBlank(movieFromRequest.getDirector()) && !movieFromRequest.getDirector().equals(movieFromDb.getDirector())) {
            movieFromDb.setDirector(movieFromRequest.getDirector());
        }
        if (movieFromRequest.getReleaseYear() != null && movieFromRequest.getReleaseYear() > 1800 && !movieFromRequest.getReleaseYear().equals(movieFromDb.getReleaseYear())) {
            movieFromDb.setReleaseYear(movieFromRequest.getReleaseYear());
        }
        if (movieFromRequest.getRuntimeMinutes() != null && movieFromRequest.getRuntimeMinutes() > 0 && !movieFromRequest.getRuntimeMinutes().equals(movieFromDb.getRuntimeMinutes())) {
            movieFromDb.setRuntimeMinutes(movieFromRequest.getRuntimeMinutes());
        }

        return movieRepository.save(movieFromDb);
    }

    public void deleteMovieById(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found"));

        movieRepository.delete(movie);
    }
}