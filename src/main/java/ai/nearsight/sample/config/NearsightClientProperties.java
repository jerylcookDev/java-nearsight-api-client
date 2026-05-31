package ai.nearsight.sample.config;

 
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nearsight.client")
public record NearsightClientProperties(
        String baseUrl,     // e.g. https://api.nearsight.ai
        String apiKey       // injected from env / secret, never hardcoded
) {}