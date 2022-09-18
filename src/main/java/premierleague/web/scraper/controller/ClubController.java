package premierleague.web.scraper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premierleague.web.scraper.dto.ClubDto;
import premierleague.web.scraper.exception.ClubNotFoundException;
import premierleague.web.scraper.exception.DocumentParseException;
import premierleague.web.scraper.service.ClubService;

@RestController
@RequestMapping("/data/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService teamService) {
        this.clubService = teamService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getClubsData() {
        try {
            return new ResponseEntity<>(clubService.getAllClubs(), HttpStatus.OK);
        } catch (DocumentParseException e) {
            return new ResponseEntity<>(e.getTitle(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClubData(@PathVariable Long id) {
        try {
            ClubDto clubDto = clubService.getClub(id);
            return new ResponseEntity<>(clubDto, HttpStatus.OK);
        } catch (ClubNotFoundException e) {
            return new ResponseEntity<>(e.getTitle(), HttpStatus.NOT_FOUND);
        }
    }

}
