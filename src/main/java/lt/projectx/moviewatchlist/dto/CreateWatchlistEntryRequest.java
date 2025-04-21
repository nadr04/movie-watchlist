package lt.projectx.moviewatchlist.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateWatchlistEntryRequest(
        @NotBlank(message = "Movie ID cannot be empty")
        String movieId,

        @NotBlank(message = "Watcher ID cannot be empty")
        String watcherId,

        @NotNull(message = "Status cannot be null")
        String status,

        @DecimalMin(value = "1.0", inclusive = true, message = "Rating must be between 1 and 10")
        @DecimalMax(value = "10.0", inclusive = true, message = "Rating must be between 1 and 10")
        BigDecimal rating
) {
}
