package lt.projectx.moviewatchlist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Watcher {
    @Id
    private String id;
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    private String name;
    private LocalDate joinDate;
    @OneToMany(mappedBy = "watcher", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<WatchlistEntry> watchlistEntries;

    public Watcher(String id, String username, String email, String name, LocalDate joinDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.joinDate = joinDate;

    }
}
