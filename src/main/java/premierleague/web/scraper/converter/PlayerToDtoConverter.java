package premierleague.web.scraper.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import premierleague.web.scraper.dto.ClubDto;
import premierleague.web.scraper.dto.PlayerDto;
import premierleague.web.scraper.model.Player;
import premierleague.web.scraper.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlayerToDtoConverter {

    private final PlayerRepository playerRepository;

    public PlayerToDtoConverter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerDto> convert() {
        List<Player> players = (List<Player>) playerRepository.findAll();
        List<PlayerDto> result = new ArrayList<>();
        players.forEach(player -> result.add(new PlayerDto(player.getId(), player.getName(), player.getPosition(), player.getNumber(), player.getNationality(), new ClubDto(player.getClub().getId(), player.getClub().getName(), player.getClub().getStadium()))));
        return result;
    }

    public PlayerDto convertToDto(Player player) {
        ClubDto clubDto = new ClubDto(player.getClub().getId(), player.getClub().getName(), player.getClub().getStadium());
        return new PlayerDto(player.getId(), player.getName(), player.getPosition(), player.getNumber(), player.getNationality(), clubDto);
    }

}
