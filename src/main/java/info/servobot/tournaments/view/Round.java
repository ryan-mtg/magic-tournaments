package info.servobot.tournaments.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Round {
    private List<Match> matches = new ArrayList<>();
    private boolean last;
    private int matchHeight;
    private double matchBoxTop;
    private double connectorHeight;
}