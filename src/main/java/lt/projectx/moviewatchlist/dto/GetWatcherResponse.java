package lt.projectx.moviewatchlist.dto;

public record GetWatcherResponse(
        String id,
        String username,
        String email,
        String name,
        String joinDate
) {
}
