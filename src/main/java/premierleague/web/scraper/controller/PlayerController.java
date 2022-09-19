package premierleague.web.scraper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premierleague.web.scraper.dto.ErrorMessageDto;
import premierleague.web.scraper.dto.PlayerDto;
import premierleague.web.scraper.exception.PlayerNotFoundException;
import premierleague.web.scraper.service.PlayerService;

@RestController
@RequestMapping("/data/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getPlayersData() {
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPlayerData(@PathVariable Long id) {
        try {
            PlayerDto playerDto = playerService.getPlayer(id);
            return new ResponseEntity<>(playerDto, HttpStatus.OK);
        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(new ErrorMessageDto(e.getTitle()), HttpStatus.NOT_FOUND);
        }
    }

}
