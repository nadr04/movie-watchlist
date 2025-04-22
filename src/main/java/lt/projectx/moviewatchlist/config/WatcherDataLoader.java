package lt.projectx.moviewatchlist.config;

import lt.projectx.moviewatchlist.entity.Watcher;
import lt.projectx.moviewatchlist.repository.WatcherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class WatcherDataLoader implements CommandLineRunner {

    private final WatcherRepository watcherRepository;

    public WatcherDataLoader(WatcherRepository watcherRepository) {
        this.watcherRepository = watcherRepository;
    }

    @Override
    public void run(String... args) {
        if (watcherRepository.count() == 0) {
            int idCounter = 1;

            Watcher watcher1 = new Watcher(
                    generateId(idCounter++),
                    "skvfull",
                    "dummymail@dummy.com",
                    "Nadine",
                    LocalDate.now().minusDays(10)
            );

            Watcher watcher2 = new Watcher(
                    generateId(idCounter++),
                    "movie_lover_22",
                    "lover22@movies.com",
                    "Jacob",
                    LocalDate.now().minusMonths(2)
            );

            Watcher watcher3 = new Watcher(
                    generateId(idCounter++),
                    "cinema_buff",
                    "buff@cinema.com",
                    "John Pork",
                    LocalDate.now().minusYears(1)
            );

            watcherRepository.save(watcher1);
            watcherRepository.save(watcher2);
            watcherRepository.save(watcher3);

            System.out.println("Watchers seeded!");
        } else {
            System.out.println("Watchers already exist. Skipping seeding.");
        }
    }

    private String generateId(int counter) {
        return String.format("WAT%03d", counter);
    }
}