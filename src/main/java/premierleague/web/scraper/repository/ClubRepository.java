package premierleague.web.scraper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import premierleague.web.scraper.model.Club;

@Repository
public interface ClubRepository extends CrudRepository<Club, Long> {

}
