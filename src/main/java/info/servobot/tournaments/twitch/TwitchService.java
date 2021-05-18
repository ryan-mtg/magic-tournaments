package info.servobot.tournaments.twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import info.servobot.tournaments.security.UserInfo;
import lombok.Getter;

public class TwitchService {
    @Getter
    private final String clientId;

    @Getter
    private final String secret;

    private final String oauthToken;
    private final String authToken;
    private TwitchClient client;

    public TwitchService(final String clientId, final String secret, final String oauthToken) {
        this.clientId = clientId;
        this.secret = secret;
        this.oauthToken = oauthToken;
        this.authToken = oauthToken.substring(oauthToken.indexOf(':') + 1);

    }

    public UserInfo getUserInfo(final String auth) {
        com.github.twitch4j.helix.domain.User user =
                client.getHelix().getUsers(auth, null, null).execute().getUsers().get(0);
        return new UserInfo(Integer.parseInt(user.getId()), user.getLogin());
    }

    public void start() {
        client = TwitchClientBuilder.builder().withEnableHelix(true).withClientId(clientId).withClientSecret(secret)
                .build();
    }
}
