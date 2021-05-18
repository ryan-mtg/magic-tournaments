package info.servobot.tournaments.utility;

public class Flags {
    public static boolean hasFlag(final int flags, final int flag) {
        return (flags & flag) != 0;
    }
}
