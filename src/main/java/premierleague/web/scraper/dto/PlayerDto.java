package premierleague.web.scraper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    @JsonProperty("playerId")
    private Long id;
    private String name;
    private String position;
    private int number;
    private String nationality;
    private ClubDto club;

}
