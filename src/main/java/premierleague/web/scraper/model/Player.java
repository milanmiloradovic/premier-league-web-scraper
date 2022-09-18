package premierleague.web.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "player", schema = "public")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "number")
    private int number;

    @Column(name = "nationality")
    private String nationality;

    @OneToOne()
    @JoinColumn(name = "club", referencedColumnName = "club_id", insertable = false, updatable = false)
    private Club club;

    public Player(String name, String position, int number, String nationality, Club club) {
        this.name = name;
        this.position = position;
        this.number = number;
        this.nationality = nationality;
        this.club = club;
    }

}
