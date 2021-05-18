package info.servobot.tournaments.security;

import info.servobot.tournaments.model.User;
import info.servobot.tournaments.model.UserEntity;

public class WebsiteUser implements UserEntity {
    private final User user;

    public WebsiteUser(final User user) {
        this.user = user;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    @Override
    public int getId() {
        if (user != null) {
            return user.getId();
        }
        return User.UNREGISTERED_ID;
    }

    @Override
    public boolean isAdmin() {
        if (user != null) {
            return user.isAdmin();
        }
        return false;
    }

    @Override
    public long getTwitchId() {
        if (user != null) {
            return user.getTwitchId();
        }
        return 0;
    }

    @Override
    public String getTwitchName() {
        if (user != null) {
            return user.getTwitchName();
        }
        return null;
    }
}