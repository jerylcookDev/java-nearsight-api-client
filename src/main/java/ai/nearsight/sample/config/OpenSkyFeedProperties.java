package ai.nearsight.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the OpenSky feed used by the sample.
 *
 * <p>Bound from {@code nearsight.feed.opensky} and contains flags for
 * whether the feed is enabled, the polling interval, and the bounding box
 * to query.</p>
 */
@ConfigurationProperties(prefix = "nearsight.feed.opensky")
public record OpenSkyFeedProperties(
        boolean enabled,
        int pollIntervalSeconds,
        BoundingBox box
) {
    /**
     * A simple bounding box defined by min/max lat/lng in degrees.
     */
    public record BoundingBox(double laMin, double loMin, double laMax, double loMax) {}
}
