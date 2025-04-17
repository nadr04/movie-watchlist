package lt.projectx.moviewatchlist.repository;

import lt.projectx.moviewatchlist.entity.WatchlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistEntryRepository extends JpaRepository<WatchlistEntry, String> {
    List<WatchlistEntry> findAllByWatcherId(String watcherId);

}
