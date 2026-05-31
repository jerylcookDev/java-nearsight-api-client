package ai.nearsight.sample.config;
 

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(NearsightClientProperties.class)
public class NearsightClientConfig {

    public static final String API_KEY_HEADER = "X-API-Key";

    @Bean
    WebClient nearsightWebClient(NearsightClientProperties props) {
        return WebClient.builder()
                .baseUrl(props.baseUrl())
                .defaultHeader(API_KEY_HEADER, props.apiKey())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }
}