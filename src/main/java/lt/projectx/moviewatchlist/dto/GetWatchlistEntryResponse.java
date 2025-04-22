package lt.projectx.moviewatchlist.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetWatchlistEntryResponse(
        String id,
        String movieTitle,
        String watcherUsername,
        String status,
        BigDecimal rating,
        LocalDateTime dateAdded
) {}