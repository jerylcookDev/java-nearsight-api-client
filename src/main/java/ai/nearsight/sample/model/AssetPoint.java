package ai.nearsight.sample.model;
 

import java.time.Instant;
import java.util.List;

public record AssetPoint(
        String name,
        String type,
        Instant ts,
        double lat,
        double lng,
        double speedKph,
        double heading
) {}

