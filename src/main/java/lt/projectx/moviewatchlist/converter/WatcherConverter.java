package lt.projectx.moviewatchlist.converter;

import lt.projectx.moviewatchlist.dto.CreateWatcherRequest;
import lt.projectx.moviewatchlist.dto.GetWatcherResponse;
import lt.projectx.moviewatchlist.entity.Watcher;

import java.time.LocalDate;

public class WatcherConverter {

    public static Watcher toEntity(CreateWatcherRequest request) {
        Watcher watcher = new Watcher();
        watcher.setUsername(request.username());
        watcher.setEmail(request.email());
        watcher.setName(request.name());
        watcher.setJoinDate(LocalDate.parse(request.joinDate()));
        return watcher;
    }

    public static GetWatcherResponse toResponse(Watcher watcher) {
        return new GetWatcherResponse(
                watcher.getId(),
                watcher.getUsername(),
                watcher.getEmail(),
                watcher.getName(),
                watcher.getJoinDate().toString()
        );
    }
}