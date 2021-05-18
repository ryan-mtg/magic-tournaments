package info.servobot.tournaments.data.serializers;

import info.servobot.tournaments.model.Tournament;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TournamentSerializer {
    public List<Tournament> loadTournaments() {
        return Collections.singletonList(new Tournament());
    }
}
