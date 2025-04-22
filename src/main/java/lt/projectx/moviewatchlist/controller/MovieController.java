package lt.projectx.moviewatchlist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.dto.CreateMovieRequest;
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
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody CreateMovieRequest request) {
        Movie savedMovie = movieService.addMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }
}