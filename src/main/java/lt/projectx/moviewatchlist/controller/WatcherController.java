package lt.projectx.moviewatchlist.controller;

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
    public ResponseEntity<GetWatcherResponse> addWatcher(@Valid @RequestBody CreateWatcherRequest request) {
        Watcher savedWatcher = watcherService.addWatcher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(WatcherConverter.toResponse(savedWatcher));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetWatcherResponse> getWatcherById(@PathVariable String id) {
        Watcher watcher = watcherService.findWatcherById(id);
        return ResponseEntity.ok(WatcherConverter.toResponse(watcher));
    }

    @GetMapping
    public ResponseEntity<List<GetWatcherResponse>> getAllWatchers() {
        List<Watcher> allWatchers = watcherService.getAllWatchers();
        return ResponseEntity.ok(allWatchers.stream()
                .map(WatcherConverter::toResponse)
                .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatcher(@PathVariable String id) {
        watcherService.deleteWatcherById(id);
        return ResponseEntity.noContent().build();
    }
}