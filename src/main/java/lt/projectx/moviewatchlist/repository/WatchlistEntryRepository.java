package lt.projectx.moviewatchlist.repository;

import lt.projectx.moviewatchlist.entity.WatchlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistEntryRepository extends JpaRepository<WatchlistEntry, Integer> {

}
