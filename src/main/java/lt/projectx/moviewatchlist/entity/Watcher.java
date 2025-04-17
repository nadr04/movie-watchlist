package lt.projectx.moviewatchlist.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

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

}
