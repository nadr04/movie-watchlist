package lt.projectx.moviewatchlist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class WatchlistEntry {
    @Id
    private String id;
    @ManyToOne(optional = false)
    private Movie movie;
    @ManyToOne(optional = false)
    private Watcher watcher;
    private LocalDateTime dateAdded;
    @Enumerated(EnumType.STRING)
    private WatchlistStatus status;
    @Column(precision = 3, scale = 1)
    private BigDecimal rating;
}
