package premierleague.web.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Club(String name) {
        this.name = name;
    }

}
