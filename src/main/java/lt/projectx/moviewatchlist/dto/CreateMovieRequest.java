package lt.projectx.moviewatchlist.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMovieRequest (

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Genre is required")
        String genre,

        @NotBlank(message = "Director is required")
        String director,

        @NotNull(message = "Release year is required")
        @Min(value = 1800, message = "Release year must be after 1800")
        Integer releaseYear,

        @NotNull(message = "Runtime is required")
        @Min(value = 1, message = "Runtime must be at least 1 minute")
        Integer runtimeMinutes
) {
}