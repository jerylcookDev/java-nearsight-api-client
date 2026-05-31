package ai.nearsight.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Representation of the OpenSky /states/all response.
 *
 * <p>The JSON response contains a server time and a list of state-vector
 * rows; each row is a heterogeneous array of values.</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenSkyStates(
        Long time,                  // epoch seconds, server time of snapshot
        List<List<Object>> states   // each row is a positional state vector
) {}
