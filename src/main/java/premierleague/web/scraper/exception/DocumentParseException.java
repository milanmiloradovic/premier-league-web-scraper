package premierleague.web.scraper.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocumentParseException extends Exception {

    private String title;
    private String message;

    public DocumentParseException(String title, String message) {
        this.title = title;
        this.message = message;
    }

}
