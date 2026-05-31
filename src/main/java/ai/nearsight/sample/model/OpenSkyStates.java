package ai.nearsight.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenSkyStates(
        Long time,                  // epoch seconds, server time of snapshot
        List<List<Object>> states   // each row is a positional state vector
) {}