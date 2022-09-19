package premierleague.web.scraper.service;

import org.springframework.stereotype.Service;
import premierleague.web.scraper.converter.PlayerToDtoConverter;
import premierleague.web.scraper.dto.PlayerDto;
import premierleague.web.scraper.exception.DocumentParseException;
import premierleague.web.scraper.exception.PlayerNotFoundException;
import premierleague.web.scraper.model.Player;
import premierleague.web.scraper.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerToDtoConverter playerToDtoConverter;
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerToDtoConverter playerToDtoConverter, PlayerRepository playerRepository) {
        this.playerToDtoConverter = playerToDtoConverter;
        this.playerRepository = playerRepository;
    }

    public List<PlayerDto> getAllPlayers() {
        List<Player> players = (List<Player>) playerRepository.findAll();
        return playerToDtoConverter.convert(players);
    }

    public PlayerDto getPlayer(Long id) throws PlayerNotFoundException {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException("Player with id " + id + " doesn't exist!");
        }
        return playerToDtoConverter.convertToDto(player.get());
    }

}
