package ai.nearsight.sample.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties used to configure the NearSight API client.
 *
 * <p>Bound from configuration under the {@code nearsight.client} prefix.
 * Example properties include {@code base-url} and {@code api-key}.
 */
@ConfigurationProperties(prefix = "nearsight.client")
public record NearsightClientProperties(
        String baseUrl,     // e.g. https://api.nearsight.ai
        String apiKey       // injected from env / secret, never hardcoded
) {}
