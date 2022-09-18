package premierleague.web.scraper.converter;

import org.springframework.stereotype.Service;
import premierleague.web.scraper.dto.ClubDto;
import premierleague.web.scraper.model.Club;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClubToDtoConverter {

    public List<ClubDto> convert(List<Club> clubs) {
        List<ClubDto> result = new ArrayList<>();
        clubs.forEach(club -> result.add((new ClubDto(club.getId(), club.getName(), club.getStadium()))));
        return result;
    }

    public ClubDto convertToDto(Club club) {
        return new ClubDto(club.getId(), club.getName(), club.getStadium());
    }

}
