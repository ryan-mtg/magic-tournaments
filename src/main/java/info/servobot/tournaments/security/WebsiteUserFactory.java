package info.servobot.tournaments.security;

import info.servobot.tournaments.conductors.UserConductor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebsiteUserFactory {
    private final UserConductor userConductor;

    public WebsiteUser createWebsiteUser(final Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() &&
                authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            int userId = (int) oAuth2AuthenticationToken.getPrincipal().getAttributes()
                    .get(UserService.USER_ID_PROPERTY);
            try {
                return new WebsiteUser(userConductor.getUser(userId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new WebsiteUser(null);
    }
}
