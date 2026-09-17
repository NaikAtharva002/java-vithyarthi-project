package com.landtanin.studentattendancecheck.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class providing robust time comparison and attendance session state evaluation.
 * Pure Java implementation without Android SDK dependencies.
 */
public final class AttendanceTimeUtils {

    public enum SessionStatus {
        BEFORE_START,
        ACTIVE,
        LATE,
        ENDED,
        UNKNOWN
    }

    private AttendanceTimeUtils() {
        // Prevent instantiation
    }

    /**
     * Determines the attendance session status based on current time and scheduled module milestones.
     *
     * @param currentTime   Current timestamp
     * @param checkInStart  Check-in window opening timestamp
     * @param checkInEnd    Check-in window closing timestamp
     * @param moduleEnd     Class/lecture dismissal timestamp
     * @return Evaluated SessionStatus
     */
    public static SessionStatus determineStatus(Date currentTime, Date checkInStart,
                                               Date checkInEnd, Date moduleEnd) {
        if (currentTime == null || checkInStart == null || checkInEnd == null || moduleEnd == null) {
            return SessionStatus.UNKNOWN;
        }

        long currentMillis = currentTime.getTime();
        long startMillis = checkInStart.getTime();
        long endCheckInMillis = checkInEnd.getTime();
        long endModuleMillis = moduleEnd.getTime();

        if (currentMillis < startMillis) {
            return SessionStatus.BEFORE_START;
        } else if (currentMillis <= endCheckInMillis) {
            return SessionStatus.ACTIVE;
        } else if (currentMillis <= endModuleMillis) {
            return SessionStatus.LATE;
        } else {
            return SessionStatus.ENDED;
        }
    }

    /**
     * Formats a date to HH:mm string.
     *
     * @param date Date to format
     * @return Formatted string or empty if date is null
     */
    public static String formatHoursMinutes(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        return sdf.format(date);
    }

    /**
     * Formats a time range (e.g. "From 09:00 to 11:00").
     *
     * @param start Start date
     * @param end   End date
     * @return Formatted time range string
     */
    public static String formatTimeRange(Date start, Date end) {
        return "From " + formatHoursMinutes(start) + " to " + formatHoursMinutes(end);
    }
}
