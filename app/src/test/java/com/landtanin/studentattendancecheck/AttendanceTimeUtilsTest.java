package com.landtanin.studentattendancecheck;

import com.landtanin.studentattendancecheck.util.AttendanceTimeUtils;
import com.landtanin.studentattendancecheck.util.AttendanceTimeUtils.SessionStatus;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests verifying attendance session status evaluation across time milestones:
 * - Before check-in window opens
 * - Active check-in window (on-time attendance)
 * - Late check-in window (ongoing class, grace period)
 * - Class ended (attendance closed)
 */
public class AttendanceTimeUtilsTest {

    private final long BASE_TIME = 1700000000000L; // Fixed reference timestamp
    private final Date CHECK_IN_START = new Date(BASE_TIME);                  // T = 0
    private final Date CHECK_IN_END   = new Date(BASE_TIME + 15 * 60 * 1000); // T + 15 mins
    private final Date MODULE_END     = new Date(BASE_TIME + 60 * 60 * 1000); // T + 60 mins

    @Test
    public void timeBeforeCheckInStart_returnsBeforeStart() {
        Date current = new Date(BASE_TIME - 5 * 60 * 1000); // 5 mins before start
        SessionStatus status = AttendanceTimeUtils.determineStatus(current, CHECK_IN_START, CHECK_IN_END, MODULE_END);
        assertEquals(SessionStatus.BEFORE_START, status);
    }

    @Test
    public void timeDuringCheckInWindow_returnsActive() {
        Date current = new Date(BASE_TIME + 5 * 60 * 1000); // 5 mins after start (within 15m window)
        SessionStatus status = AttendanceTimeUtils.determineStatus(current, CHECK_IN_START, CHECK_IN_END, MODULE_END);
        assertEquals(SessionStatus.ACTIVE, status);
    }

    @Test
    public void timeAtCheckInExactBoundary_returnsActive() {
        Date current = new Date(BASE_TIME + 15 * 60 * 1000); // exactly at boundary
        SessionStatus status = AttendanceTimeUtils.determineStatus(current, CHECK_IN_START, CHECK_IN_END, MODULE_END);
        assertEquals(SessionStatus.ACTIVE, status);
    }

    @Test
    public void timeAfterCheckInBeforeModuleEnd_returnsLate() {
        Date current = new Date(BASE_TIME + 30 * 60 * 1000); // 30 mins in (class ongoing, check-in closed)
        SessionStatus status = AttendanceTimeUtils.determineStatus(current, CHECK_IN_START, CHECK_IN_END, MODULE_END);
        assertEquals(SessionStatus.LATE, status);
    }

    @Test
    public void timeAfterModuleEnd_returnsEnded() {
        Date current = new Date(BASE_TIME + 90 * 60 * 1000); // after class dismissal
        SessionStatus status = AttendanceTimeUtils.determineStatus(current, CHECK_IN_START, CHECK_IN_END, MODULE_END);
        assertEquals(SessionStatus.ENDED, status);
    }

    @Test
    public void nullParameters_returnsUnknown() {
        assertEquals(SessionStatus.UNKNOWN,
                AttendanceTimeUtils.determineStatus(null, CHECK_IN_START, CHECK_IN_END, MODULE_END));
        assertEquals(SessionStatus.UNKNOWN,
                AttendanceTimeUtils.determineStatus(new Date(), null, CHECK_IN_END, MODULE_END));
    }

    @Test
    public void timeRangeFormatting_formatsCorrectly() {
        String range = AttendanceTimeUtils.formatTimeRange(CHECK_IN_START, MODULE_END);
        assertTrue("Range string should start with 'From '", range.startsWith("From "));
        assertTrue("Range string should contain ' to '", range.contains(" to "));
    }
}
