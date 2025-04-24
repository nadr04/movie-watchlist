package lt.projectx.moviewatchlist.converter;

import lt.projectx.moviewatchlist.dto.CreateMovieRequest;
import lt.projectx.moviewatchlist.dto.GetMovieResponse;
import lt.projectx.moviewatchlist.entity.Movie;

import java.util.UUID;

public class MovieConverter {

    public static Movie toEntity(CreateMovieRequest request) {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID().toString());
        movie.setTitle(request.title());
        movie.setGenre(request.genre());
        movie.setDirector(request.director());
        movie.setReleaseYear(request.releaseYear());
        movie.setRuntimeMinutes(request.runtimeMinutes());
        return movie;
    }

    public static GetMovieResponse toResponse(Movie movie) {
        return new GetMovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getDirector(),
                movie.getReleaseYear(),
                movie.getRuntimeMinutes()
        );
    }
}