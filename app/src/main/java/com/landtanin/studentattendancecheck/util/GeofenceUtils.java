package com.landtanin.studentattendancecheck.util;

/**
 * Utility class providing location, geodesic distance, and geofencing calculations.
 * Pure Java implementation without Android SDK dependencies to support host JVM unit testing.
 */
public final class GeofenceUtils {

    /** Approximate mean radius of the Earth in meters */
    public static final double EARTH_RADIUS_METERS = 6371000.0;

    /** Default check-in radius tolerance in meters */
    public static final double DEFAULT_GEOFENCE_RADIUS_METERS = 100.0;

    private GeofenceUtils() {
        // Prevent instantiation
    }

    /**
     * Validates whether given latitude and longitude coordinates are physically valid.
     *
     * @param lat Latitude (-90 to +90)
     * @param lng Longitude (-180 to +180)
     * @return true if coordinates fall within valid geographic bounds
     */
    public static boolean isValidCoordinates(double lat, double lng) {
        return lat >= -90.0 && lat <= 90.0 && lng >= -180.0 && lng <= 180.0;
    }

    /**
     * Computes the great-circle distance between two points on the Earth's surface
     * using the Haversine formula.
     *
     * @param lat1 Latitude of point 1 in degrees
     * @param lng1 Longitude of point 1 in degrees
     * @param lat2 Latitude of point 2 in degrees
     * @param lng2 Longitude of point 2 in degrees
     * @return Distance between the two points in meters
     */
    public static double calculateDistanceMeters(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2.0) * Math.sin(dLng / 2.0);

        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

        return EARTH_RADIUS_METERS * c;
    }

    /**
     * Determines whether a student location is within an acceptable geofenced radius
     * of a target location (e.g., classroom).
     *
     * @param studentLat   Current student latitude
     * @param studentLng   Current student longitude
     * @param moduleLat    Classroom/module latitude
     * @param moduleLng    Classroom/module longitude
     * @param radiusMeters Maximum allowable distance in meters
     * @return true if the student is within radiusMeters of the module, false otherwise
     */
    public static boolean isWithinRadius(double studentLat, double studentLng,
                                        double moduleLat, double moduleLng,
                                        double radiusMeters) {
        if (!isValidCoordinates(studentLat, studentLng) || !isValidCoordinates(moduleLat, moduleLng)) {
            return false;
        }
        if (radiusMeters < 0) {
            return false;
        }
        double distance = calculateDistanceMeters(studentLat, studentLng, moduleLat, moduleLng);
        return distance <= radiusMeters;
    }

    /**
     * Calculates an approximate latitude delta for a given distance in meters.
     */
    public static double metersToLatitudeDelta(double meters) {
        return meters / 111320.0;
    }

    /**
     * Calculates an approximate longitude delta for a given distance in meters at a specified latitude.
     */
    public static double metersToLongitudeDelta(double meters, double latitude) {
        double cosLat = Math.cos(Math.toRadians(latitude));
        if (Math.abs(cosLat) < 1e-6) {
            return 180.0; // Near poles
        }
        return meters / (111320.0 * cosLat);
    }
}
