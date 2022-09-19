package premierleague.web.scraper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "club", schema = "public")
public class Club implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stadium")
    private String stadium;

    @Column(name = "overview")
    private String overview;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "club")
    @ToString.Exclude
    private List<Player> players;

    public Club(String name, String stadium, String overview) {
        this.name = name;
        this.stadium = stadium;
        this.overview = overview;
    }

}
