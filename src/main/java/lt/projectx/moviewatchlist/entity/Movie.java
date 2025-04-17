package lt.projectx.moviewatchlist.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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
    private String title;
    private String genre;
    private String director;
    private Integer releaseYear;
    private Integer runtimeMinutes;
}