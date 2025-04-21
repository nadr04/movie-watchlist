package lt.projectx.moviewatchlist.dto;

import java.util.Optional;

public record UpdateMovieRequest(
        Optional<String> title,
        Optional<String> genre,
        Optional<String> director,
        Optional<Integer> releaseYear,
        Optional<Integer> runtimeMinutes
) {
}
