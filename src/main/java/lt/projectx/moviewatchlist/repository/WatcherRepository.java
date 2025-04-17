package lt.projectx.moviewatchlist.repository;

import lt.projectx.moviewatchlist.entity.Watcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatcherRepository extends JpaRepository<Watcher, String> {

}
