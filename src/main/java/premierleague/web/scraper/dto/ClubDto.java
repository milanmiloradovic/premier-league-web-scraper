package premierleague.web.scraper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {

    @JsonProperty("clubId")
    private Long id;
    private String name;
    private String stadium;

}
