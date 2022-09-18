package premierleague.web.scraper.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import premierleague.web.scraper.converter.PlayerToDtoConverter;
import premierleague.web.scraper.dto.PlayerDto;
import premierleague.web.scraper.exception.DocumentParseException;
import premierleague.web.scraper.exception.PlayerNotFoundException;
import premierleague.web.scraper.model.Club;
import premierleague.web.scraper.model.Player;
import premierleague.web.scraper.repository.ClubRepository;
import premierleague.web.scraper.repository.PlayerRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PlayerService {

    @Value("${premier.league.url}")
    private String premierLeagueUrl;

    private final PlayerToDtoConverter playerToDtoConverter;
    private final ClubRepository clubRepository;
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerToDtoConverter playerToDtoConverter, ClubRepository clubRepository, PlayerRepository playerRepository) {
        this.playerToDtoConverter = playerToDtoConverter;
        this.clubRepository = clubRepository;
        this.playerRepository = playerRepository;
    }

    public List<PlayerDto> getAllPlayers() throws DocumentParseException {
        List<PlayerDto> playersDto = new ArrayList<>();
        List<Club> clubs = (List<Club>) clubRepository.findAll();
        for (Club club : clubs) {
            try {
                String sufix = club.getOverview().substring(0, club.getOverview().lastIndexOf("/")).concat("/squad");
                Document document = Jsoup.connect(premierLeagueUrl + sufix).get();
                List<Player> players = parsePlayer(document, club);
                playerRepository.saveAll(players);
                playersDto = playerToDtoConverter.convert(players);
            } catch (IOException e) {
                throw new DocumentParseException("Can't parse HTML document!", e.getMessage());
            }
        }
        return playersDto;
    }

    public List<Player> parsePlayer(Document document, Club club) {
        List<Player> players = new ArrayList<>();
        Elements items = document.getElementsByClass("playerOverviewCard");
        items.forEach(item -> {
            int number = !item.getElementsByClass("number").text().equals("-") ? Integer.parseInt(item.getElementsByClass("number").text()) : -1;
            players.add(new Player(item.getElementsByClass("name").text(), item.getElementsByClass("position").text(), number, item.getElementsByClass("playerCountry").text(), club));
        });
        return players;
    }

    public PlayerDto getPlayer(Long id) throws PlayerNotFoundException {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException("Player with id " + id + " doesn't exist!");
        }
        return playerToDtoConverter.convertToDto(player.get());
    }
}
