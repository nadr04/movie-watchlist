package lt.projectx.moviewatchlist.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.converter.WatcherConverter;
import lt.projectx.moviewatchlist.dto.CreateWatcherRequest;
import lt.projectx.moviewatchlist.dto.GetWatcherResponse;
import lt.projectx.moviewatchlist.entity.Watcher;
import lt.projectx.moviewatchlist.service.WatcherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchers")
@RequiredArgsConstructor
@Validated
public class WatcherController {
    private final WatcherService watcherService;

    @PostMapping
    public ResponseEntity<Watcher> addWatcher(@Valid @RequestBody CreateWatcherRequest request) {
        Watcher savedWatcher = watcherService.addWatcher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWatcher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Watcher> getWatcherById(@PathVariable String id) {
        return ResponseEntity.ok(watcherService.findWatcherById(id));
    }

    @GetMapping
    public ResponseEntity<List<Watcher>> getAllWatchers() {
        return ResponseEntity.ok(watcherService.getAllWatchers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatcher(@PathVariable String id) {
        watcherService.deleteWatcherById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GetWatcherResponse>> filterWatchers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name
    ) {
        List<Watcher> filtered = watcherService.filterWatchers(username, email, name);

        if (filtered.isEmpty()) {
            throw new EntityNotFoundException("No watchers found matching the provided filters.");
        }

        return ResponseEntity.ok(filtered.stream().map(WatcherConverter::toResponse).toList());
    }
}