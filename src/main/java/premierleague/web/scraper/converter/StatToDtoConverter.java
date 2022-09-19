package premierleague.web.scraper.converter;

import org.springframework.stereotype.Service;
import premierleague.web.scraper.dto.ClubDto;
import premierleague.web.scraper.dto.StatDto;
import premierleague.web.scraper.model.Club;
import premierleague.web.scraper.model.Stat;

@Service
public class StatToDtoConverter {

    public StatDto convert(Club club) {
        Stat stat = club.getStat();
        ClubDto clubDto = new ClubDto(club.getId(), club.getName(), club.getStadium());
        return new StatDto(stat.getMatchesPlayed(), stat.getWins(), stat.getLosses(), stat.getGoals(), stat.getGoalsConceded(), stat.getCleanSheets(), clubDto);
    }

}
