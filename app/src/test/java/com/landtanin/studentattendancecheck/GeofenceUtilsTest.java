package com.landtanin.studentattendancecheck;

import com.landtanin.studentattendancecheck.util.GeofenceUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests verifying GPS distance calculation, coordinate validation,
 * and geofence boundary verification logic.
 */
public class GeofenceUtilsTest {

    private static final double EPSILON = 5.0; // 5 meters tolerance for geospatial rounding

    @Test
    public void validCoordinates_returnsTrue() {
        assertTrue(GeofenceUtils.isValidCoordinates(0.0, 0.0));
        assertTrue(GeofenceUtils.isValidCoordinates(12.9716, 77.5946)); // Bengaluru
        assertTrue(GeofenceUtils.isValidCoordinates(-33.8688, 151.2093)); // Sydney
        assertTrue(GeofenceUtils.isValidCoordinates(90.0, 180.0));
        assertTrue(GeofenceUtils.isValidCoordinates(-90.0, -180.0));
    }

    @Test
    public void invalidCoordinates_returnsFalse() {
        assertFalse(GeofenceUtils.isValidCoordinates(90.001, 0.0));
        assertFalse(GeofenceUtils.isValidCoordinates(-90.1, 0.0));
        assertFalse(GeofenceUtils.isValidCoordinates(0.0, 180.001));
        assertFalse(GeofenceUtils.isValidCoordinates(0.0, -180.5));
    }

    @Test
    public void distanceBetweenIdenticalCoordinates_isZero() {
        double lat = 12.9698;
        double lng = 79.1559; // VIT Vellore approx
        double distance = GeofenceUtils.calculateDistanceMeters(lat, lng, lat, lng);
        assertEquals(0.0, distance, 0.001);
    }

    @Test
    public void distanceBetweenKnownCoordinates_isAccurate() {
        // Paris (48.8566, 2.3522) to London (51.5074, -0.1278) ~ 343 km = 343,000 meters
        double parisLat = 48.8566, parisLng = 2.3522;
        double londonLat = 51.5074, londonLng = -0.1278;
        double distance = GeofenceUtils.calculateDistanceMeters(parisLat, parisLng, londonLat, londonLng);

        // Expected approx 343,556 meters
        assertTrue("Distance between Paris and London should be approx 343.5km",
                distance > 340000 && distance < 350000);
    }

    @Test
    public void isWithinRadius_studentInsideClassroom_returnsTrue() {
        double classroomLat = 12.969200;
        double classroomLng = 79.155900;

        // Student 20 meters away (approx 0.00018 deg latitude difference)
        double studentLat = 12.969350;
        double studentLng = 79.155900;

        double distance = GeofenceUtils.calculateDistanceMeters(studentLat, studentLng, classroomLat, classroomLng);
        assertTrue("Distance should be under 50m", distance < 50.0);
        assertTrue("Student within 100m geofence",
                GeofenceUtils.isWithinRadius(studentLat, studentLng, classroomLat, classroomLng, 100.0));
    }

    @Test
    public void isWithinRadius_studentOutsideCampus_returnsFalse() {
        double classroomLat = 12.969200;
        double classroomLng = 79.155900;

        // Student 1 km away
        double studentLat = 12.979000;
        double studentLng = 79.155900;

        assertFalse("Student 1km away should be rejected by 100m geofence",
                GeofenceUtils.isWithinRadius(studentLat, studentLng, classroomLat, classroomLng, 100.0));
    }

    @Test
    public void isWithinRadius_negativeRadius_returnsFalse() {
        assertFalse(GeofenceUtils.isWithinRadius(12.0, 79.0, 12.0, 79.0, -10.0));
    }

    @Test
    public void isWithinRadius_invalidCoordinates_returnsFalse() {
        assertFalse(GeofenceUtils.isWithinRadius(999.0, 79.0, 12.0, 79.0, 100.0));
        assertFalse(GeofenceUtils.isWithinRadius(12.0, 79.0, 12.0, 999.0, 100.0));
    }
}
