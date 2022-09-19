package premierleague.web.scraper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premierleague.web.scraper.dto.ClubDto;
import premierleague.web.scraper.dto.ErrorMessageDto;
import premierleague.web.scraper.dto.PlayerDto;
import premierleague.web.scraper.dto.StatDto;
import premierleague.web.scraper.exception.ClubNotFoundException;
import premierleague.web.scraper.service.ClubService;

import java.util.List;

@RestController
@RequestMapping("/data/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService teamService) {
        this.clubService = teamService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getClubsData() {
        return new ResponseEntity<>(clubService.getAllClubs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClubData(@PathVariable Long id) {
        try {
            ClubDto clubDto = clubService.getClub(id);
            return new ResponseEntity<>(clubDto, HttpStatus.OK);
        } catch (ClubNotFoundException e) {
            return new ResponseEntity<>(new ErrorMessageDto(e.getTitle()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<Object> getClubPlayersData(@PathVariable Long id) {
        try {
            List<PlayerDto> playerDtos = clubService.getPlayersByClub(id);
            return new ResponseEntity<>(playerDtos, HttpStatus.OK);
        } catch (ClubNotFoundException e) {
            return new ResponseEntity<>(new ErrorMessageDto(e.getTitle()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Object> getClubStatData(@PathVariable Long id) {
        try {
            StatDto statDtos = clubService.getStatsByClub(id);
            return new ResponseEntity<>(statDtos, HttpStatus.OK);
        } catch (ClubNotFoundException e) {
            return new ResponseEntity<>(new ErrorMessageDto(e.getTitle()), HttpStatus.NOT_FOUND);
        }
    }


}
