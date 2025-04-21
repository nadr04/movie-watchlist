package lt.projectx.moviewatchlist.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetWatchlistEntryResponse(
        String id,
        GetMovieResponse movie,
        GetWatcherResponse watcher,
        lt.projectx.moviewatchlist.entity.WatchlistStatus status,
        BigDecimal rating,
        LocalDateTime dateAdded
) {
}
