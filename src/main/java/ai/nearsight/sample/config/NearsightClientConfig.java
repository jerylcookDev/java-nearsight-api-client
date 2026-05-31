package ai.nearsight.sample.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration that provides a pre-configured {@link WebClient} for
 * communicating with the NearSight API.
 *
 * <p>The client sets the base URL and the required API key header
 * (configured via {@link NearsightClientProperties}).</p>
 */
@Configuration
@EnableConfigurationProperties(NearsightClientProperties.class)
public class NearsightClientConfig {

    public static final String API_KEY_HEADER = "X-API-Key";

    /**
     * Build a {@link WebClient} pre-configured for the NearSight API.
     *
     * @param props injected properties containing baseUrl and apiKey
     * @return a ready-to-use WebClient instance
     */
    @Bean
    WebClient nearsightWebClient(NearsightClientProperties props) {
        return WebClient.builder()
                .baseUrl(props.baseUrl())
                .defaultHeader(API_KEY_HEADER, props.apiKey())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }
}
