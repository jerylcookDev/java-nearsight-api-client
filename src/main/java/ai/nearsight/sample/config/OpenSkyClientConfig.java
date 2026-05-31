package ai.nearsight.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configures a {@link WebClient} for calls to the OpenSky API.
 *
 * <p>The client is set up with an OAuth2 authorized client manager and a
 * default client registration id of {@code opensky}. OAuth2 client
 * registrations should be provided via Spring configuration (see
 * {@code application.yaml}).</p>
 */
@Configuration
public class OpenSkyClientConfig {

    @Bean
    WebClient openSkyWebClient(
            ReactiveClientRegistrationRepository registrations,
            ReactiveOAuth2AuthorizedClientService clientService) {

        var authorizedClientManager =
            new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(registrations, clientService);

        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("opensky");

        return WebClient.builder()
                .baseUrl("https://opensky-network.org/api")
                .filter(oauth)
                .build();
    }
}
