package lt.projectx.moviewatchlist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Movie {
    @Id
    private String id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Genre cannot be blank")
    private String genre;

    @NotBlank(message = "Director cannot be blank")
    private String director;

    @NotNull(message = "Release year is required")
    private Integer releaseYear;

    @NotNull(message = "Runtime is required")
    private Integer runtimeMinutes;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<WatchlistEntry> watchlistEntries = new ArrayList<>();

    public Movie(String id, String title, String genre, String director, Integer releaseYear, Integer runtimeMinutes) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.releaseYear = releaseYear;
        this.runtimeMinutes = runtimeMinutes;
    }
}