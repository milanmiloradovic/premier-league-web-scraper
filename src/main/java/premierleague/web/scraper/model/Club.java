package premierleague.web.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "club", schema = "public")
public class Club implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stadium")
    private String stadium;

    @Column(name = "overview")
    private String overview;

    public Club(String name, String stadium, String overview) {
        this.name = name;
        this.stadium = stadium;
        this.overview = overview;
    }

}
