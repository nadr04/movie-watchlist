package lt.projectx.moviewatchlist.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.entity.WatchlistEntry;
import lt.projectx.moviewatchlist.repository.WatchlistEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistEntryService {
    private final WatchlistEntryRepository watchlistEntryRepository;

    public WatchlistEntry addWatchlistEntry(WatchlistEntry watchlistEntry) {
        return watchlistEntryRepository.saveAndFlush(watchlistEntry);
    }

    public List<WatchlistEntry> getAllWatchlistEntries() {
        return watchlistEntryRepository.findAll();
    }

    public WatchlistEntry findWatchlistEntryById(String id) {
        return watchlistEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watchlist entry with id " + id + " not found"));
    }

    public WatchlistEntry patchWatchlistEntryById(String id, WatchlistEntry entryFromRequest) {
        WatchlistEntry entryFromDb = watchlistEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watchlist entry with id " + id + " not found"));

        if (entryFromRequest.getMovie() != null &&
                !entryFromRequest.getMovie().equals(entryFromDb.getMovie())) {
            entryFromDb.setMovie(entryFromRequest.getMovie());
        }

        if (entryFromRequest.getDateAdded() != null &&
                !entryFromRequest.getDateAdded().equals(entryFromDb.getDateAdded())) {
            if (entryFromRequest.getDateAdded().isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("Date added cannot be in the future.");
            }
            entryFromDb.setDateAdded(entryFromRequest.getDateAdded());
        }

        if (entryFromRequest.getStatus() != null &&
                !entryFromRequest.getStatus().equals(entryFromDb.getStatus())) {
            entryFromDb.setStatus(entryFromRequest.getStatus());
        }

        if (entryFromRequest.getRating() != null &&
                entryFromRequest.getRating().compareTo(entryFromDb.getRating()) != 0) {
            if (entryFromRequest.getRating().compareTo(BigDecimal.ONE) < 0 ||
                    entryFromRequest.getRating().compareTo(BigDecimal.TEN) > 0) {
                throw new IllegalArgumentException("Rating must be between 1 and 10.");
            }
            entryFromDb.setRating(entryFromRequest.getRating());
        }
        return watchlistEntryRepository.saveAndFlush(entryFromDb);
    }

    public List<WatchlistEntry> getWatchlistEntriesByWatcherId(String watcherId) {
        return watchlistEntryRepository.findAllByWatcherId(watcherId);
    }
    public void deleteWatchlistEntryById(String id) {
        WatchlistEntry entry = watchlistEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watchlist entry with id " + id + " not found"));
        watchlistEntryRepository.delete(entry);
    }
}