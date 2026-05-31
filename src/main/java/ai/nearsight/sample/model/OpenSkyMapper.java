package ai.nearsight.sample.model;

import java.time.Instant;
import java.util.List;

/** Maps an OpenSky positional state vector to a TrackedAsset. */
	public final class OpenSkyMapper {

	    // OpenSky /states/all field indices
	    private static final int ICAO24       = 0;
	    private static final int CALLSIGN     = 1;
	    private static final int TIME_POS     = 3;   // epoch seconds, nullable
	    private static final int LAST_CONTACT = 4;   // epoch seconds
	    private static final int LONGITUDE    = 5;   // nullable
	    private static final int LATITUDE     = 6;   // nullable
	    private static final int ON_GROUND    = 8;
	    private static final int VELOCITY     = 9;   // m/s
	    private static final int TRUE_TRACK   = 10;  // heading degrees

	    private static final double MS_TO_KPH = 3.6;
	    private static final String TYPE_AIRCRAFT = "aircraft";

	    private OpenSkyMapper() {}

	    /** Returns null for rows with no usable position — caller should skip these. */
	    public static TrackedAsset toTrackedAsset(List<Object> s) {
	        if (s == null || s.size() < 11) {
	            return null;
	        }

	        Double lat = asDouble(s.get(LATITUDE));
	        Double lng = asDouble(s.get(LONGITUDE));
	        if (lat == null || lng == null) {
	            return null;   // aircraft without a position fix — not ingestable
	        }

	        String icao = trimToNull(asString(s.get(ICAO24)));
	        if (icao == null) {
	            return null;   // no stable identifier
	        }

	        TrackedAsset asset = new TrackedAsset();
	        asset.setName( icao);   // standardize on uppercase hex
	        asset.setType(TYPE_AIRCRAFT);
	        asset.setLatitude(lat);
	        asset.setLongitude(lng);

	        Double velocityMs = asDouble(s.get(VELOCITY));
	        asset.setSpeed(velocityMs == null ? null : velocityMs * MS_TO_KPH);   // -> km/h

	        asset.setBearing(asDouble(s.get(TRUE_TRACK)));

	        Long timePos = asLong(s.get(TIME_POS));
	        Long lastContact = asLong(s.get(LAST_CONTACT));
	        long epochSeconds = timePos != null ? timePos
	                          : lastContact != null ? lastContact
	                          : Instant.now().getEpochSecond();
	        asset.setTimestamp(epochSeconds * 1000);   // store as epoch millis

	        Boolean onGround = asBoolean(s.get(ON_GROUND));
	        asset.setStatus(Boolean.TRUE.equals(onGround) ? "ON_GROUND" : "AIRBORNE");

	        asset.setInformation(trimToNull(asString(s.get(CALLSIGN))));   // flight number
	        return asset;
	    }

	    // --- safe positional extraction (array elements are String/Number/Boolean/null) ---

	    private static String asString(Object o) {
	        return o instanceof String str ? str : null;
	    }

	    private static Double asDouble(Object o) {
	        return o instanceof Number n ? n.doubleValue() : null;
	    }

	    private static Long asLong(Object o) {
	        return o instanceof Number n ? n.longValue() : null;
	    }

	    private static Boolean asBoolean(Object o) {
	        return o instanceof Boolean b ? b : null;
	    }

	    private static String trimToNull(String s) {
	        if (s == null) return null;
	        String t = s.trim();
	        return t.isEmpty() ? null : t;
	    }
	}