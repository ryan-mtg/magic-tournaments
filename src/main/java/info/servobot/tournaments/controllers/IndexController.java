package info.servobot.tournaments.controllers;

import info.servobot.tournaments.view.Bracket;
import info.servobot.tournaments.view.Match;
import info.servobot.tournaments.view.Result;
import info.servobot.tournaments.view.Round;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index.html"})
    public String index(final Model model) {
        return "index";
    }

    @RequestMapping("/bracket.html")
    public String bracketExample(final Model model) {
        model.addAttribute("bracket", createFakeTournament());
        return "test";
    }

    private Bracket createFakeTournament() {
        Bracket bracket = new Bracket();

        Round round1 = new Round();
        for (int i = 0; i < 8; i++) {
            Match match = new Match();
            match.setTeam1(new Result(String.format("Player %d", 2 * i + 1), 12343 * (2 * i + 1) % 7));
            match.setTeam2(new Result(String.format("Player %d", 2 * i + 2), 12343 * (2 * i + 2) % 7));
            match.setUp(i % 2 == 0);
            round1.getMatches().add(match);
        }
        round1.setLast(false);
        round1.setMatchHeight(65);
        round1.setMatchBoxTop(9);
        round1.setConnectorHeight(20.75);
        bracket.getRounds().add(round1);

        Round round2 = new Round();
        for (int i = 0; i < 4; i++) {
            Match match = new Match();
            match.setTeam1(new Result(String.format("Player %d", 4 * i + 1), 12343 * (4 * i + 1) % 7));
            match.setTeam2(new Result(String.format("Player %d", 4 * i + 3), 12343 * (4 * i + 3) % 7));
            match.setUp(i % 2 == 0);
            round2.getMatches().add(match);
        }
        round2.setLast(false);
        round2.setMatchHeight(130);
        round2.setMatchBoxTop(41.5);
        round2.setConnectorHeight(53.25);
        bracket.getRounds().add(round2);

        Round round3 = new Round();
        for (int i = 0; i < 2; i++) {
            Match match = new Match();
            match.setTeam1(new Result(String.format("Player %d", 8 * i + 1), 12343 * (8 * i + 1) % 7));
            match.setTeam2(new Result(String.format("Player %d", 8 * i + 5), 12343 * (8 * i + 5) % 7));
            match.setUp(i % 2 == 0);
            round3.getMatches().add(match);
        }
        round3.setLast(false);
        round3.setMatchHeight(260);
        round3.setMatchBoxTop(106.5);
        round3.setConnectorHeight(118.25);
        bracket.getRounds().add(round3);

        Round round4 = new Round();
        for (int i = 0; i < 1; i++) {
            Match match = new Match();
            match.setTeam1(new Result(String.format("Player %d", 16 * i + 1), 12343 * (16 * i + 1) % 7));
            match.setTeam2(new Result(String.format("Player %d", 16 * i + 9), 12343 * (16 * i + 9) % 7));
            match.setUp(i % 2 == 0);
            round4.getMatches().add(match);
        }
        round4.setLast(true);
        round3.setMatchHeight(260);
        round4.setMatchHeight(520);
        round4.setMatchBoxTop(236.5);
        round4.setConnectorHeight(218.25);
        bracket.getRounds().add(round4);

        return bracket;
    }
}