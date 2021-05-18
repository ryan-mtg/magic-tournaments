package info.servobot.tournaments.conductors;

import info.servobot.tournaments.model.Tournament;

import java.util.ArrayList;
import java.util.List;

public class TournamentConductor {
    private List<Tournament> tournaments = new ArrayList<>();

    public void addTournaments(final List<Tournament> newTournaments) {
        tournaments.addAll(newTournaments);
    }
}