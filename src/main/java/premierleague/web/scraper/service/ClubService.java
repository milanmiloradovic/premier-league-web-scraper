package premierleague.web.scraper.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import premierleague.web.scraper.converter.ClubToDtoConverter;
import premierleague.web.scraper.dto.ClubDto;
import premierleague.web.scraper.exception.ClubNotFoundException;
import premierleague.web.scraper.exception.DocumentParseException;
import premierleague.web.scraper.model.Club;
import premierleague.web.scraper.repository.ClubRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClubService {

    @Value("${premier.league.url}")
    private String premierLeagueUrl;

    private final ClubToDtoConverter clubToDtoConverter;
    private final ClubRepository clubRepository;

    public ClubService(ClubToDtoConverter clubToDtoConverter, ClubRepository clubRepository) {
        this.clubToDtoConverter = clubToDtoConverter;
        this.clubRepository = clubRepository;
    }

    public List<ClubDto> getAllClubs() throws DocumentParseException {
        List<ClubDto> clubs;
        try {
            List<Club> names = new ArrayList<>();
            Elements elements = Jsoup.connect(premierLeagueUrl + "/clubs?se=489").get().getElementsByClass("clubName");
            elements.forEach(element -> names.add(new Club(element.text())));
            clubRepository.saveAll(names);
            clubs = clubToDtoConverter.convert(names);
        } catch (IOException e) {
            throw new DocumentParseException("Can't parse HTML document!", e.getMessage());
        }
        return clubs;
    }

    public ClubDto getClub(Long id) throws ClubNotFoundException {
        Optional<Club> club = clubRepository.findById(id);
        if (club.isEmpty()) {
            throw new ClubNotFoundException("Club with id " + id + " doesn't exist!");
        }
        return clubToDtoConverter.convertToDto(club.get());
    }
}
