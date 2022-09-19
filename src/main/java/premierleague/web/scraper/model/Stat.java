package premierleague.web.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stat", schema = "public")
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    private Long id;

    @Column(name = "matches_played")
    private int matchesPlayed;

    @Column(name = "wins")
    private int wins;

    @Column(name = "losses")
    private int losses;

    @Column(name = "goals")
    private int goals;

    @Column(name = "goals_conceded")
    private int goalsConceded;

    @Column(name = "clean_sheets")
    private int cleanSheets;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public Stat(int matchesPlayed, int wins, int losses, int goals, int goalsConceded, int cleanSheets, Club club) {
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.losses = losses;
        this.goals = goals;
        this.goalsConceded = goalsConceded;
        this.cleanSheets = cleanSheets;
        this.club = club;
    }

}
