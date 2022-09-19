package premierleague.web.scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto {

    private int matchesPlayed;
    private int wins;
    private int losses;
    private int goals;
    private int goalsConceded;
    private int cleanSheets;

}
