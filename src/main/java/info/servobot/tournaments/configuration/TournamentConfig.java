package info.servobot.tournaments.configuration;

import info.servobot.tournaments.conductors.TournamentConductor;
import info.servobot.tournaments.conductors.UserConductor;
import info.servobot.tournaments.data.serializers.TournamentSerializer;
import info.servobot.tournaments.data.serializers.UserSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TournamentConfig {
    private final TournamentSerializer tournamentSerializer;
    private final UserSerializer userSerializer;

    @Bean
    public TournamentConductor tournamentConductor() {
        TournamentConductor tournamentConductor = new TournamentConductor();
        tournamentConductor.addTournaments(tournamentSerializer.loadTournaments());
        return tournamentConductor;
    }

    @Bean
    public UserConductor userConductor() {
        return new UserConductor(userSerializer);
    }
}
