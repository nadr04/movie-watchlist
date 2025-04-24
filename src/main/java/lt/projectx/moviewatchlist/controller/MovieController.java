package lt.projectx.moviewatchlist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.converter.MovieConverter;
import lt.projectx.moviewatchlist.dto.CreateMovieRequest;
import lt.projectx.moviewatchlist.dto.GetMovieResponse;
import lt.projectx.moviewatchlist.entity.Movie;
import lt.projectx.moviewatchlist.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<GetMovieResponse> addMovie(@Valid @RequestBody CreateMovieRequest request) {
        Movie savedMovie = movieService.addMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieConverter.toResponse(savedMovie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMovieResponse> getMovieById(@PathVariable String id) {
        Movie movie = movieService.findMovieById(id);
        return ResponseEntity.ok(MovieConverter.toResponse(movie));
    }

    @GetMapping
    public ResponseEntity<List<GetMovieResponse>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        List<GetMovieResponse> response = movies.stream()
                .map(MovieConverter::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filters")
    public ResponseEntity<?> filterMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) Integer releaseYear
    ) {
        if (title == null && genre == null && director == null && releaseYear == null) {
            return ResponseEntity.badRequest().body("At least one filter parameter must be provided.");
        }

        List<Movie> filtered = movieService.filterMovies(title, genre, director, releaseYear);

        if (filtered.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No movies match the provided filters.");
        }

        return ResponseEntity.ok(filtered.stream()
                .map(MovieConverter::toResponse)
                .toList());
    }
}