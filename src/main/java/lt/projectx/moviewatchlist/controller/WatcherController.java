package lt.projectx.moviewatchlist.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.entity.Watcher;
import lt.projectx.moviewatchlist.service.WatcherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/watchers")
@RequiredArgsConstructor
public class WatcherController {
    private final WatcherService watcherService;

    @PostMapping
    public ResponseEntity<Watcher> addWatcher(@Valid @RequestBody Watcher watcher) {
        Watcher createdWatcher = watcherService.addWatcher(watcher);
        return ResponseEntity
                .created(URI.create("/watchers/" + createdWatcher.getId()))
                .body(createdWatcher);
    }

    @GetMapping
    public ResponseEntity<List<Watcher>> getAllWatchers() {
        List<Watcher> watchers = watcherService.getAllWatchers();
        return ResponseEntity.ok(watchers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Watcher> getWatcherById(@PathVariable String id) {
        try {
            Watcher watcher = watcherService.findWatcherById(id);
            return ResponseEntity.ok(watcher);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Watcher> updateWatcher(@PathVariable String id, @RequestBody Watcher watcher) {
        try {
            Watcher updatedWatcher = watcherService.patchWatcherById(id, watcher);
            return ResponseEntity.ok(updatedWatcher);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatcher(@PathVariable String id) {
        try {
            watcherService.deleteWatcherById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
