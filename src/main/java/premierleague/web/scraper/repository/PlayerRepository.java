package premierleague.web.scraper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import premierleague.web.scraper.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

}
