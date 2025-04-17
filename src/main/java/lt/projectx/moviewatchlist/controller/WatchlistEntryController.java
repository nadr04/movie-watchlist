package lt.projectx.moviewatchlist.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.entity.WatchlistEntry;
import lt.projectx.moviewatchlist.service.WatchlistEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/watchlistEntries")
@RequiredArgsConstructor
public class WatchlistEntryController {
    private final WatchlistEntryService watchlistEntryService;

    @PostMapping
    public ResponseEntity<WatchlistEntry> addWatchlistEntry(@Valid @RequestBody WatchlistEntry watchlistEntry) {
        watchlistEntry.setDateAdded(LocalDateTime.now());
        WatchlistEntry createdWatchlistEntry = watchlistEntryService.addWatchlistEntry(watchlistEntry);
        return ResponseEntity
                .created(URI.create("/watchlistEntries/" + createdWatchlistEntry.getId()))
                .body(createdWatchlistEntry);
    }

    @GetMapping
    public ResponseEntity<List<WatchlistEntry>> getAllWatchlistEntries() {
        List<WatchlistEntry> watchlistEntries = watchlistEntryService.getAllWatchlistEntries();
        return ResponseEntity.ok(watchlistEntries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WatchlistEntry> getWatchlistEntryById(@PathVariable String id) {
        try {
            WatchlistEntry watchlistEntry = watchlistEntryService.findWatchlistEntryById(id);
            return ResponseEntity.ok(watchlistEntry);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/watcher/{watcherId}")
    public ResponseEntity<List<WatchlistEntry>> getWatchlistEntriesByWatcherId(@PathVariable String watcherId) {
        try {
            List<WatchlistEntry> entries = watchlistEntryService.getWatchlistEntriesByWatcherId(watcherId);
            return ResponseEntity.ok(entries);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WatchlistEntry> updateWatchlistEntry(@PathVariable String id, @RequestBody WatchlistEntry watchlistEntry) {
        try {
            WatchlistEntry updatedWatchlistEntry = watchlistEntryService.patchWatchlistEntryById(id, watchlistEntry);
            return ResponseEntity.ok(updatedWatchlistEntry);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlistEntry(@PathVariable String id) {
        try {
            watchlistEntryService.deleteWatchlistEntryById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
