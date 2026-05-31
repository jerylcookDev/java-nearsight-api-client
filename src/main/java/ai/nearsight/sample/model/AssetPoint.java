package ai.nearsight.sample.model;


import java.time.Instant;
import java.util.List;

/**
 * Lightweight immutable representation of an asset sample point.
 *
 * <p>Used for small internal transformations or examples; fields include a
 * name, type, timestamp, position and simple kinematic values.</p>
 *
 * @param name human- or system-readable identifier
 * @param type asset type (for example, "aircraft")
 * @param ts  timestamp of the sample
 * @param lat latitude in decimal degrees
 * @param lng longitude in decimal degrees
 * @param speedKph speed in km/h
 * @param heading heading in degrees
 */
public record AssetPoint(
        String name,
        String type,
        Instant ts,
        double lat,
        double lng,
        double speedKph,
        double heading
) {}
