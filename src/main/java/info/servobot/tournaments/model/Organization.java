package info.servobot.tournaments.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    public static final int UNREGISTERED_ID = 0;
    public static final int TOURNAMENT_ORGANIZER_FLAG = 1;
    public static final int ADMIN_FLAG = 2;

    private int id;
    private String name;
    private List<User> tournamentOrganizers;
    private List<User> tournamentAdmins;
}
