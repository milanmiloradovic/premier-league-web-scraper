package premierleague.web.scraper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClubNotFoundException extends RuntimeException {

    private String title;

    public ClubNotFoundException(String title) {
        this.title = title;
    }

}
