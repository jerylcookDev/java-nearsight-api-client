package ai.nearsight.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
 
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
 
/**
 * Domain model representing an asset tracked by NearSight.
 *
 * <p>The class is intentionally a POJO with Jakarta Validation annotations
 * so instances can be validated before ingest. Fields include identity,
 * position (latitude/longitude), kinematic data (speed/bearing) and
 * optional informational fields.</p>
 */
public class TrackedAsset {
 
    @NotNull
    private String name;
    
    @NotNull
    private Long timestamp;
   
    @NotNull
    private String type;
   
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;
 
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;
 
    @DecimalMin("0.0")
    @DecimalMax("360.0")
    private Double bearing;
 
    @Min(0)
    private Double speed;
 
   
    private String status;
 
    private String information;

    /** Returns the asset status (for example, "ON_GROUND" or "AIRBORNE"). */
    public String getStatus() { return status; }

    /** Set the asset status. */
    public void setStatus(String status) { this.status = status; }

    /** Returns the latitude in decimal degrees. */
    public Double getLatitude() { return latitude; }

    /** Set the latitude in decimal degrees. */
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    /** Returns the longitude in decimal degrees. */
    public Double getLongitude() { return longitude; }

    /** Set the longitude in decimal degrees. */
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    /** Returns the asset name/identifier (ICAO24 or similar). */
    public String getName() { return name; }

    /** Set the asset name/identifier. */
    public void setName(String name) { this.name = name; }

    /** Returns the asset type (example: "aircraft"). */
    public String getType() { return type; }

    /** Set the asset type. */
    public void setType(String type) { this.type = type; }

    /** Returns the timestamp (epoch millis) associated with this point. */
    public Long getTimestamp() { return timestamp; }

    /** Set the timestamp (epoch millis). */
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    /** Returns the bearing/heading in degrees. */
    public Double getBearing() { return bearing; }

    /** Set the bearing/heading in degrees. */
    public void setBearing(Double bearing) { this.bearing = bearing; }

    /** Returns the speed in km/h. */
    public Double getSpeed() { return speed; }

    /** Set the speed in km/h. */
    public void setSpeed(Double speed) { this.speed = speed; }

    /** Optional free-form information string (flight number, etc.). */
    public String getInformation() { return information; }

    /** Set the optional information field. */
    public void setInformation(String information) { this.information = information; }
}
