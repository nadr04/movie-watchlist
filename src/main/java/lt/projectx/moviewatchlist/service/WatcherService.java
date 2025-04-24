package lt.projectx.moviewatchlist.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.dto.CreateWatcherRequest;
import lt.projectx.moviewatchlist.entity.Watcher;
import lt.projectx.moviewatchlist.repository.WatcherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatcherService {
    private final WatcherRepository watcherRepository;

    private String generateWatcherId() {
        String prefix = "WAT";
        int totalLength = 6;
        int currentCount = watcherRepository.findAll().size() + 1;
        String numberPart = String.format("%0" + (totalLength - prefix.length()) + "d", currentCount);
        return prefix + numberPart;
    }

    public Watcher addWatcher(CreateWatcherRequest request) {
        Watcher watcher = new Watcher();
        watcher.setId(generateWatcherId());
        watcher.setUsername(request.username());
        watcher.setEmail(request.email());
        watcher.setName(request.name());
        watcher.setJoinDate(LocalDate.parse(request.joinDate()));
        return watcherRepository.save(watcher);
    }

    public List<Watcher> getAllWatchers() {
        return watcherRepository.findAll();
    }

    public Watcher findWatcherById(String id) {
        return watcherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watcher with id " + id + " not found"));
    }
    public void deleteWatcherById(String id) {
        Watcher watcher = watcherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watcher with id " + id + " not found"));

        watcherRepository.delete(watcher);
    }
}