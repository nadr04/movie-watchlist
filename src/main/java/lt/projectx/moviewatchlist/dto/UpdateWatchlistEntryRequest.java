package lt.projectx.moviewatchlist.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record UpdateWatchlistEntryRequest(
        String status,
        @DecimalMin(value = "1", inclusive = true, message = "Rating must be between 1 and 10")
        @DecimalMax(value = "10", inclusive = true, message = "Rating must be between 1 and 10")
        BigDecimal rating
) {}