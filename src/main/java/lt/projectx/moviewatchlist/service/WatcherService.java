package lt.projectx.moviewatchlist.service;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.projectx.moviewatchlist.converter.WatcherConverter;
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

    public Watcher patchWatcherById(String id, Watcher watcherFromRequest) {
        Watcher watcherFromDb = watcherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watcher with id " + id + " not found"));

        if (StringUtils.isNotBlank(watcherFromRequest.getUsername()) &&
                !watcherFromRequest.getUsername().equals(watcherFromDb.getUsername())) {
            watcherFromDb.setUsername(watcherFromRequest.getUsername());
        }

        if (StringUtils.isNotBlank(watcherFromRequest.getEmail()) &&
                !watcherFromRequest.getEmail().equals(watcherFromDb.getEmail())) {
            watcherFromDb.setEmail(watcherFromRequest.getEmail());
        }

        if (StringUtils.isNotBlank(watcherFromRequest.getName()) &&
                !watcherFromRequest.getName().equals(watcherFromDb.getName())) {
            watcherFromDb.setName(watcherFromRequest.getName());
        }

        if (watcherFromRequest.getJoinDate() != null &&
                !watcherFromRequest.getJoinDate().equals(watcherFromDb.getJoinDate())) {
            if (watcherFromRequest.getJoinDate().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Join date cannot be in the future.");
            }
            watcherFromDb.setJoinDate(watcherFromRequest.getJoinDate());
        }

        return watcherRepository.saveAndFlush(watcherFromDb);
    }
    public void deleteWatcherById(String id) {
        Watcher watcher = watcherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Watcher with id " + id + " not found"));

        watcherRepository.delete(watcher);
    }
}