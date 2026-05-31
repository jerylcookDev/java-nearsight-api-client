package ai.nearsight.sample.config;
 
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nearsight.feed.opensky")
public record OpenSkyFeedProperties(
        boolean enabled,
        int pollIntervalSeconds,
        BoundingBox box
) {
    public record BoundingBox(double laMin, double loMin, double laMax, double loMax) {}
}