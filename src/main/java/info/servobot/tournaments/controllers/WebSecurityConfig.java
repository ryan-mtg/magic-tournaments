package info.servobot.tournaments.controllers;

import info.servobot.tournaments.conductors.UserConductor;
import info.servobot.tournaments.security.TwitchUserService;
import info.servobot.tournaments.security.WebsiteUserFactory;
import info.servobot.tournaments.twitch.TwitchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ControllerAdvice
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final WebsiteUserFactory websiteUserFactory;
    private final TwitchService twitchService;
    private final UserConductor userConductor;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/foo").authenticated()
                .antMatchers("/**").permitAll()
            .and().oauth2Login().userInfoEndpoint()
                .userService(new TwitchUserService(twitchService, userConductor));

        /*
        http.authorizeRequests().antMatchers("/", "/index.html", "/scripts/**", "/styles/**", "faveicon.ico", "/images/**").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().loginPage("/login").permitAll()
            .and().logout() .permitAll();
         */
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(final List<ClientRegistration> registrations) {
        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public ClientRegistration twitchClientRegistration() {
        return ClientRegistration.withRegistrationId("twitch")
                .clientId(twitchService.getClientId())
                .clientSecret(twitchService.getSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .authorizationUri("https://id.twitch.tv/oauth2/authorize")
                .tokenUri("https://id.twitch.tv/oauth2/token")
                .userNameAttributeName("data")
                .clientName("twitch")
                .build();
    }

    @ModelAttribute
    private void addUser(final Model model, final OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        model.addAttribute("user", websiteUserFactory.createWebsiteUser(oAuth2AuthenticationToken));
    }
}
