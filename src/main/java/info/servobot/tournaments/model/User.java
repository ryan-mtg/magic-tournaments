package info.servobot.tournaments.model;

import info.servobot.tournaments.utility.Flags;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class User implements UserEntity {
    public static final int UNREGISTERED_ID = 0;
    public static final int DEFAULT_FLAGS = 0;
    private static final int ADMIN_FLAG = 1;

    private int id;
    private int flags;
    private long twitchId;
    private String twitchName;

    @Override
    public boolean isAdmin() {
        return Flags.hasFlag(flags, ADMIN_FLAG);
    }
}