package info.servobot.tournaments.view;

import info.servobot.tournaments.view.Round;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tournament {
    private List<Round> rounds = new ArrayList<>();
}
