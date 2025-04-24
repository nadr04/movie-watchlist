package lt.projectx.moviewatchlist.controller;

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

import java.math.BigDecimal;
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
    public ResponseEntity<?> filterWatchlistEntries(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) BigDecimal rating,
            @RequestParam(required = false) String genre
    ) {
        if (status == null && rating == null && genre == null) {
            return ResponseEntity
                    .badRequest()
                    .body("At least one of [status, rating, genre] must be provided.");
        }

        List<WatchlistEntry> filtered = watchlistEntryService.filterEntries(status, rating, genre);

        if (filtered.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No watchlist entries match the provided filters.");
        }

        List<GetWatchlistEntryResponse> dtoList = filtered.stream()
                .map(WatchlistEntryConverter::toResponse)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}