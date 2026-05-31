package ai.nearsight.sample.model;

/**
 * Simple immutable container for a latitude/longitude coordinate.
 *
 * @param latitude decimal degrees latitude
 * @param longitude decimal degrees longitude
 */
public record NearPoint(double latitude, double longitude) {}