package lt.projectx.moviewatchlist.dto;

public record GetMovieResponse(
        String id,
        String title,
        String genre,
        String director,
        Integer releaseYear,
        Integer runtimeMinutes
) {
}

