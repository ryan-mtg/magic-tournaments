package info.servobot.tournaments.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {
    private final long id;
    private final String username;
}
