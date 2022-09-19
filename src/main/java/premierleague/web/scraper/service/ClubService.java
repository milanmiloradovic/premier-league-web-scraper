package premierleague.web.scraper.service;

import org.springframework.stereotype.Service;
import premierleague.web.scraper.converter.ClubToDtoConverter;
import premierleague.web.scraper.converter.PlayerToDtoConverter;
import premierleague.web.scraper.converter.StatToDtoConverter;
import premierleague.web.scraper.dto.ClubDto;
import premierleague.web.scraper.dto.PlayerDto;
import premierleague.web.scraper.dto.StatDto;
import premierleague.web.scraper.exception.ClubNotFoundException;
import premierleague.web.scraper.exception.DocumentParseException;
import premierleague.web.scraper.model.Club;
import premierleague.web.scraper.repository.ClubRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubToDtoConverter clubToDtoConverter;
    private final PlayerToDtoConverter playerToDtoConverter;
    private final StatToDtoConverter statToDtoConverter;
    private final ClubRepository clubRepository;

    public ClubService(ClubToDtoConverter clubToDtoConverter, PlayerToDtoConverter playerToDtoConverter, StatToDtoConverter statToDtoConverter, ClubRepository clubRepository) {
        this.clubToDtoConverter = clubToDtoConverter;
        this.playerToDtoConverter = playerToDtoConverter;
        this.statToDtoConverter = statToDtoConverter;
        this.clubRepository = clubRepository;
    }

    public List<ClubDto> getAllClubs()  {
        List<Club> clubs = (List<Club>) clubRepository.findAll();
        return clubToDtoConverter.convert(clubs);
    }

    public ClubDto getClub(Long id) throws ClubNotFoundException {
        Optional<Club> club = clubRepository.findById(id);
        if (club.isEmpty()) {
            throw new ClubNotFoundException("Club with id " + id + " doesn't exist!");
        }
        return clubToDtoConverter.convertToDto(club.get());
    }

    public List<PlayerDto> getPlayersByClub(Long id) {
        Optional<Club> club = clubRepository.findById(id);
        if (club.isEmpty()) {
            throw new ClubNotFoundException("Club with id " + id + " doesn't exist!");
        }
        return playerToDtoConverter.convert(club.get().getPlayers());
    }

    public StatDto getStatsByClub(Long id) {
        Optional<Club> club = clubRepository.findById(id);
        if (club.isEmpty()) {
            throw new ClubNotFoundException("Club with id " + id + " doesn't exist!");
        }
        return statToDtoConverter.convert(club.get());
    }

}
