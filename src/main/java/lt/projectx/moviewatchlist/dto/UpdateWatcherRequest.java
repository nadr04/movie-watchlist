package lt.projectx.moviewatchlist.dto;

import java.util.Optional;

public record UpdateWatcherRequest(
        Optional<String> username,
        Optional<String> email,
        Optional<String> name,
        Optional<String> joinDate
) {
}
