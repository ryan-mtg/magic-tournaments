package info.servobot.tournaments.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bracket {
    private List<Round> rounds = new ArrayList<>();
}
