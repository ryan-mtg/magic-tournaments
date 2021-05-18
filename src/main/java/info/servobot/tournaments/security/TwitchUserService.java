package info.servobot.tournaments.security;

import info.servobot.tournaments.conductors.UserConductor;
import info.servobot.tournaments.model.User;
import info.servobot.tournaments.twitch.TwitchService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwitchUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserConductor userConductor;
    private final TwitchService twitchService;

    public TwitchUserService(final TwitchService twitchService, final UserConductor userConductor) {
        this.twitchService = twitchService;
        this.userConductor = userConductor;
    }

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = new HashMap<>();

        User user;
        String registrar = userRequest.getClientRegistration().getRegistrationId();
        if (registrar.equals("twitch")) {
            UserInfo userInfo = twitchService.getUserInfo(userRequest.getAccessToken().getTokenValue());
            user = userConductor.getUserByTwitchId(userInfo.getId(), userInfo.getUsername());
        } else {
            throw new RuntimeException(String.format("Unknown service: %s", registrar));
        }

        List<GrantedAuthority> authorityList =
                AuthorityUtils.createAuthorityList("ROLE_USER", String.format("ROLE_ID:%d", user.getId()));

        if (user.isAdmin()) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        /*
        for (Integer homeId : userTable.getHomesModerated(user.getId())) {
            authorityList.add(new SimpleGrantedAuthority(String.format("ROLE_MOD:%d", homeId)));
        }

        for (Integer homeId : userTable.getHomesStreamed(user.getId())) {
            authorityList.add(new SimpleGrantedAuthority(String.format("ROLE_STREAMER:%d", homeId)));
        }
         */

        attributes.put(UserService.USER_ID_PROPERTY, user.getId());
        attributes.put("oauth_token", userRequest.getAccessToken().getTokenValue());
        attributes.put("name", user.getTwitchName());

        return new DefaultOAuth2User(authorityList, attributes, "name");
    }
}