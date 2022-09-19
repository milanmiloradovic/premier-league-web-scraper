package premierleague.web.scraper.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import premierleague.web.scraper.model.Club;
import premierleague.web.scraper.model.Player;
import premierleague.web.scraper.model.Stat;
import premierleague.web.scraper.repository.ClubRepository;
import premierleague.web.scraper.repository.PlayerRepository;
import premierleague.web.scraper.repository.StatRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebScraper {

    @Value("${premier.league.url}")
    private String premierLeagueUrl;

    private final ClubRepository clubRepository;
    private final PlayerRepository playerRepository;
    private final StatRepository statRepository;

    public WebScraper(ClubRepository clubRepository, PlayerRepository playerRepository, StatRepository statRepository) {
        this.clubRepository = clubRepository;
        this.playerRepository = playerRepository;
        this.statRepository = statRepository;
    }

    @PostConstruct
    private void insertClubs() {
        try {
            Document document = Jsoup.connect(premierLeagueUrl + "/clubs?se=489").get();
            List<Club> clubs = parseClub(document);
            clubRepository.saveAll(clubs);
            log.info("Clubs have been saved!");
        } catch (IOException e) {
            log.error("Can't parse HTML document for clubs! Message: " + e.getMessage());
        }
    }

    @PostConstruct
    private void insertPlayers() {
        List<Club> clubs = (List<Club>) clubRepository.findAll();
        for (Club club : clubs) {
            try {
                String sufix = club.getOverview().substring(0, club.getOverview().lastIndexOf("/")).concat("/squad");
                Document document = Jsoup.connect(premierLeagueUrl + sufix).get();
                List<Player> players = parsePlayer(document, club);
                playerRepository.saveAll(players);
            } catch (IOException e) {
                log.error("Can't parse HTML document for players! Message: " + e.getMessage());
            }
        }
        log.info("Players have been saved!");
    }

    @PostConstruct
    private void insertClubStats() {
        List<Club> clubs = (List<Club>) clubRepository.findAll();
        for (Club club : clubs) {
            try {
                String sufix = club.getOverview().substring(0, club.getOverview().lastIndexOf("/")).concat("/stats");
                Document document = Jsoup.connect(premierLeagueUrl + sufix).timeout(8000).get();
                List<Stat> stats = parseStat(document, club);
                statRepository.saveAll(stats);
            } catch (IOException e) {
                log.error("Can't parse HTML document for club stats! Message: " + e.getMessage());
            }
        }
        log.info("Stats have been saved!");
    }

    public List<Club> parseClub(Document document) {
        List<Club> clubs = new ArrayList<>();
        Elements items = document.getElementsByClass("indexItem");
        items.forEach(item -> clubs.add(new Club(item.select("h4").text(), item.getElementsByClass("stadiumName").text(), item.select("a").attr("href"))));
        return clubs;
    }

    public List<Player> parsePlayer(Document document, Club club) {
        List<Player> players = new ArrayList<>();
        Elements items = document.getElementsByClass("playerOverviewCard");
        items.forEach(item -> {
            int number = !item.getElementsByClass("number").text().equals("-") ? Integer.parseInt(item.getElementsByClass("number").text()) : -1;
            players.add(new Player(item.getElementsByClass("name").text(), item.getElementsByClass("position").text(), number, item.getElementsByClass("playerCountry").text(), club));
        });
        return players;
    }

    public List<Stat> parseStat(Document document, Club club) {
        List<Stat> stats = new ArrayList<>();
        Elements items = document.getElementsByClass("topStatList");
        items.forEach(item -> {
            int matchesPlayed = Integer.parseInt(item.getElementsByClass("allStatContainer statmatches_played").text().replace(",", ""));
            int wins = Integer.parseInt(item.getElementsByClass("allStatContainer statwins").text().replace(",", ""));
            int losses = Integer.parseInt(item.getElementsByClass("allStatContainer statlosses").text().replace(",", ""));
            int goals = Integer.parseInt(item.getElementsByClass("allStatContainer statgoals").text().replace(",", ""));
            int goalsConceded = !item.getElementsByClass("allStatContainer statgoal_conceded").text().contains("") ? Integer.parseInt(item.getElementsByClass("allStatContainer statgoal_conceded").text().replace(",", "")) : 0;
            int cleanSheets = Integer.parseInt(item.getElementsByClass("allStatContainer statclean_sheet").text().replace(",", ""));
            stats.add(new Stat(matchesPlayed, wins, losses, goals, goalsConceded, cleanSheets, club));
        });
        return stats;
    }

}
