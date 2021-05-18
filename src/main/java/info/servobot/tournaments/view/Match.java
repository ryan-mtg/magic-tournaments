package info.servobot.tournaments.view;

import lombok.Data;

@Data
public class Match {
    private Result team1;
    private Result team2;
    private boolean up;
}