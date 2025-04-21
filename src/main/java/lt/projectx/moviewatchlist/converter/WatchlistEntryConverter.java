package lt.projectx.moviewatchlist.converter;

import lt.projectx.moviewatchlist.dto.CreateWatchlistEntryRequest;
import lt.projectx.moviewatchlist.dto.GetWatchlistEntryResponse;
import lt.projectx.moviewatchlist.dto.UpdateWatchlistEntryRequest;
import lt.projectx.moviewatchlist.entity.Movie;
import lt.projectx.moviewatchlist.entity.Watcher;
import lt.projectx.moviewatchlist.entity.WatchlistEntry;
import lt.projectx.moviewatchlist.entity.WatchlistStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WatchlistEntryConverter {

    public static WatchlistEntry toEntity(CreateWatchlistEntryRequest request, Movie movie, Watcher watcher) {
        WatchlistEntry entry = new WatchlistEntry();
        entry.setMovie(movie);
        entry.setWatcher(watcher);
        entry.setStatus(WatchlistStatus.valueOf(request.status()));
        entry.setRating(request.rating());
        entry.setDateAdded(LocalDateTime.now());
        return entry;
    }

    public static GetWatchlistEntryResponse toResponse(WatchlistEntry entry) {
        return new GetWatchlistEntryResponse(
                entry.getId(),
                MovieConverter.toResponse(entry.getMovie()),
                WatcherConverter.toResponse(entry.getWatcher()),
                entry.getStatus(),
                entry.getRating(),
                entry.getDateAdded()
        );
    }

    public static WatchlistEntry toEntity(UpdateWatchlistEntryRequest request, WatchlistEntry existingEntry) {
        if (request.status() != null) {
            try {
                existingEntry.setStatus(WatchlistStatus.valueOf(request.status()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + request.status());
            }
        }

        if (request.rating() != null) {
            if (request.rating().compareTo(BigDecimal.ONE) < 0 || request.rating().compareTo(BigDecimal.TEN) > 0) {
                throw new IllegalArgumentException("Rating must be between 1 and 10.");
            }
            existingEntry.setRating(request.rating());
        }

        return existingEntry;
    }
}