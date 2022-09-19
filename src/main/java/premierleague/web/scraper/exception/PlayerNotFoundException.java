package premierleague.web.scraper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerNotFoundException extends RuntimeException {

    private String title;

    public PlayerNotFoundException(String title) {
        this.title = title;
    }

}
