package info.servobot.tournaments.model;

public interface UserEntity {
    int getId();
    boolean isAdmin();
    long getTwitchId();
    String getTwitchName();
}
