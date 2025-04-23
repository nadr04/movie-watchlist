package lt.projectx.moviewatchlist.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.dto.CreateWatchlistEntryRequest;
import lt.projectx.moviewatchlist.dto.GetWatchlistEntryResponse;
import lt.projectx.moviewatchlist.dto.UpdateWatchlistEntryRequest;
import lt.projectx.moviewatchlist.entity.WatchlistEntry;
import lt.projectx.moviewatchlist.service.WatchlistEntryService;
import lt.projectx.moviewatchlist.converter.WatchlistEntryConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlistEntries")
@RequiredArgsConstructor
public class WatchlistEntryController {

    private final WatchlistEntryService watchlistEntryService;

    @PostMapping
    public ResponseEntity<GetWatchlistEntryResponse> addWatchlistEntry(@Valid @RequestBody CreateWatchlistEntryRequest request) {
        WatchlistEntry entry = watchlistEntryService.addWatchlistEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(WatchlistEntryConverter.toResponse(entry));
    }

    @GetMapping
    public ResponseEntity<List<GetWatchlistEntryResponse>> getAllWatchlistEntries() {
        List<WatchlistEntry> entries = watchlistEntryService.getAllWatchlistEntries();
        List<GetWatchlistEntryResponse> response = entries.stream()
                .map(WatchlistEntryConverter::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetWatchlistEntryResponse> getWatchlistEntryById(@PathVariable String id) {
        WatchlistEntry entry = watchlistEntryService.findWatchlistEntryById(id);
        return ResponseEntity.ok(WatchlistEntryConverter.toResponse(entry));
    }

    @GetMapping("/watcher/{watcherId}")
    public ResponseEntity<List<GetWatchlistEntryResponse>> getWatchlistEntriesByWatcherId(@PathVariable String watcherId) {
        List<WatchlistEntry> entries = watchlistEntryService.getWatchlistEntriesByWatcherId(watcherId);
        List<GetWatchlistEntryResponse> response = entries.stream()
                .map(WatchlistEntryConverter::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetWatchlistEntryResponse> updateWatchlistEntry(
            @PathVariable String id,
            @RequestBody @Valid UpdateWatchlistEntryRequest request
    ) {
        WatchlistEntry updatedEntry = watchlistEntryService.updateWatchlistEntry(id, request);
        return ResponseEntity.ok(WatchlistEntryConverter.toResponse(updatedEntry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlistEntry(@PathVariable String id) {
        watchlistEntryService.deleteWatchlistEntryById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GetWatchlistEntryResponse>> filterWatchlistEntries(
            @RequestParam(required = false) String movieTitle,
            @RequestParam(required = false) String watcherUsername,
            @RequestParam(required = false) String status
    ) {
        List<WatchlistEntry> filtered = watchlistEntryService.filterEntries(movieTitle, watcherUsername, status);

        if (filtered.isEmpty()) {
            throw new EntityNotFoundException("No watchlist entries found matching the provided filters.");
        }

        return ResponseEntity.ok(filtered.stream().map(WatchlistEntryConverter::toResponse).toList());
    }
}