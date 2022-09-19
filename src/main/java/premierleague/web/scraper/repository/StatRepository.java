package premierleague.web.scraper.repository;

import jdk.jfr.Registered;
import org.springframework.data.repository.CrudRepository;
import premierleague.web.scraper.model.Stat;

@Registered
public interface StatRepository extends CrudRepository<Stat, Long> {

}
