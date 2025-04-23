package lt.projectx.moviewatchlist.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.converter.WatchlistEntryConverter;
import lt.projectx.moviewatchlist.dto.CreateWatchlistEntryRequest;
import lt.projectx.moviewatchlist.dto.UpdateWatchlistEntryRequest;
import lt.projectx.moviewatchlist.entity.Movie;
import lt.projectx.moviewatchlist.entity.Watcher;
import lt.projectx.moviewatchlist.entity.WatchlistEntry;
import lt.projectx.moviewatchlist.entity.WatchlistStatus;
import lt.projectx.moviewatchlist.repository.MovieRepository;
import lt.projectx.moviewatchlist.repository.WatcherRepository;
import lt.projectx.moviewatchlist.repository.WatchlistEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistEntryService {
    private final WatchlistEntryRepository watchlistEntryRepository;
    private final MovieRepository movieRepository;
    private final WatcherRepository watcherRepository;

    private String generateWatchlistEntryId() {
        String prefix = "WLE";
        int totalLength = 9;
        int currentCount = watchlistEntryRepository.findAll().size() + 1;
        String numberPart = String.format("%0" + (totalLength - prefix.length()) + "d", currentCount);
        return prefix + numberPart;
    }

    public WatchlistEntry addWatchlistEntry(CreateWatchlistEntryRequest request) {
        if (request.rating() != null &&
                (request.rating().compareTo(BigDecimal.ONE) < 0 || request.rating().compareTo(BigDecimal.TEN) > 0)) {
            throw new IllegalArgumentException("Rating must be between 1 and 10.");
        }

        Movie movie = movieRepository.findById(request.movieId())
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        Watcher watcher = watcherRepository.findById(request.watcherId())
                .orElseThrow(() -> new EntityNotFoundException("Watcher not found"));

        WatchlistEntry entry = WatchlistEntryConverter.toEntity(request, movie, watcher);
        entry.setId(generateWatchlistEntryId());
        entry.setDateAdded(LocalDateTime.now());

        return watchlistEntryRepository.save(entry);
    }

    public List<WatchlistEntry> getAllWatchlistEntries() {
        return watchlistEntryRepository.findAll();
    }

    public WatchlistEntry findWatchlistEntryById(String id) {
        return watchlistEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watchlist entry with id " + id + " not found"));
    }

    public WatchlistEntry updateWatchlistEntry(String id, UpdateWatchlistEntryRequest request) {
        WatchlistEntry entry = watchlistEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watchlist entry not found"));

        if (request.status() != null) {
            try {
                entry.setStatus(WatchlistStatus.valueOf(request.status()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + request.status());
            }
        }

        if (request.rating() != null) {
            if (request.rating().compareTo(BigDecimal.ONE) < 0 || request.rating().compareTo(BigDecimal.TEN) > 0) {
                throw new IllegalArgumentException("Rating must be between 1 and 10.");
            }
            entry.setRating(request.rating());
        }

        return watchlistEntryRepository.save(entry);
    }

    public List<WatchlistEntry> getWatchlistEntriesByWatcherId(String watcherId) {
        return watchlistEntryRepository.findAllByWatcherId(watcherId);
    }
    public void deleteWatchlistEntryById(String id) {
        WatchlistEntry entry = watchlistEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watchlist entry with id " + id + " not found"));
        watchlistEntryRepository.delete(entry);
    }

    public List<WatchlistEntry> filterEntries(String movieTitle, String watcherUsername, String status) {
        return watchlistEntryRepository.findAll().stream()
                .filter(entry -> movieTitle == null || entry.getMovie().getTitle().equalsIgnoreCase(movieTitle))
                .filter(entry -> watcherUsername == null || entry.getWatcher().getUsername().equalsIgnoreCase(watcherUsername))
                .filter(entry -> status == null || entry.getStatus().name().equalsIgnoreCase(status))
                .toList();
    }
}