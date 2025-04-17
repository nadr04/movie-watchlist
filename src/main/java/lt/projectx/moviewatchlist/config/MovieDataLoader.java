package lt.projectx.moviewatchlist.config;

import lt.projectx.moviewatchlist.entity.Movie;
import lt.projectx.moviewatchlist.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MovieDataLoader implements CommandLineRunner {

    private final MovieRepository movieRepository;

    public MovieDataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {

            int idCounter = 1;

            Movie[] movies = {
                    // Horror Movies
                    new Movie(generateId(idCounter++), "The Conjuring", "Horror", "James Wan", 2013, 112),
                    new Movie(generateId(idCounter++), "The Conjuring 2", "Horror", "James Wan", 2016, 134),
                    new Movie(generateId(idCounter++), "Drag Me to Hell", "Horror", "Sam Raimi", 2009, 99),
                    new Movie(generateId(idCounter++), "The Cabin in the Woods", "Horror", "Drew Goddard", 2012, 95),
                    new Movie(generateId(idCounter++), "Hereditary", "Horror", "Ari Aster", 2018, 127),
                    new Movie(generateId(idCounter++), "It Follows", "Horror", "David Robert Mitchell", 2014, 100),
                    new Movie(generateId(idCounter++), "Train to Busan", "Horror", "Yeon Sang-ho", 2016, 118),

                    // Drama Movies
                    new Movie(generateId(idCounter++), "Forrest Gump", "Drama", "Robert Zemeckis", 1994, 142),
                    new Movie(generateId(idCounter++), "Me Before You", "Drama", "Thea Sharrock", 2016, 110),
                    new Movie(generateId(idCounter++), "Miracle in Cell No. 7", "Drama", "Mehmet Ada Öztekin", 2019, 132),
                    new Movie(generateId(idCounter++), "The Pianist", "Drama", "Roman Polanski", 2002, 150),
                    new Movie(generateId(idCounter++), "The Fault in Our Stars", "Drama", "Josh Boone", 2014, 126),
                    new Movie(generateId(idCounter++), "Dune", "Drama", "Denis Villeneuve", 2021, 155),

                    // Thriller Movies
                    new Movie(generateId(idCounter++), "Zodiac", "Thriller", "David Fincher", 2007, 157),
                    new Movie(generateId(idCounter++), "The Girl with the Dragon Tattoo", "Thriller", "David Fincher", 2011, 158),
                    new Movie(generateId(idCounter++), "Gone Girl", "Thriller", "David Fincher", 2014, 149),

                    // Comedy Movies
                    new Movie(generateId(idCounter++), "Bridesmaids", "Comedy", "Paul Feig", 2011, 125),
                    new Movie(generateId(idCounter++), "White Chicks", "Comedy", "Keenen Ivory Wayans", 2004, 109),
                    new Movie(generateId(idCounter++), "The Babysitter", "Comedy/Horror", "McG", 2017, 85),
                    new Movie(generateId(idCounter++), "Pitch Perfect", "Comedy/Music", "Jason Moore", 2012, 112),
                    new Movie(generateId(idCounter++), "Mean Girls", "Comedy", "Mark Waters", 2004, 97),
                    new Movie(generateId(idCounter++), "Easy A", "Comedy/Romance", "Will Gluck", 2010, 92),
                    new Movie(generateId(idCounter++), "Legally Blonde", "Comedy", "Robert Luketic", 2001, 96)
            };

            for (Movie movie : movies) {
                movieRepository.save(movie);
            }

            System.out.println("Movies seeded!");
        } else {
            System.out.println("Movies already exist. Skipping seeding.");
        }
    }

    private String generateId(int counter) {
        return String.format("MOV%05d", counter);
    }
}