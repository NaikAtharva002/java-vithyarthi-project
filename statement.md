# Project Statement: Real-Time Attendance Monitoring System

**Course / Academic Track:** VITyarthi Java Android Project  
**Project Title:** Real-Time Attendance Monitoring System  

---

## 1. Problem Statement
Accurate attendance tracking is vital for evaluating student participation and fulfilling institutional accreditation requirements. However, traditional attendance recording mechanisms—such as physical sign-in sheets, paper roll-calls, and unverified digital web portals—suffer from systemic flaws:
- **Malpractice and Proxy Attendance:** Students easily sign or respond for absent peers without physical accountability.
- **Time Inefficiency:** Taking attendance verbally in large lectures detracts 10–15% of instructional time.
- **Data Inconsistencies:** Physical logs are vulnerable to loss, damage, and transcription errors when manually entered into university management systems.
- **Absence of Location Verification:** Conventional software forms allow students to check in remotely without actually attending class.

There is a critical academic need for an automated, mobile-first attendance monitoring system that couples user authentication with real-time GPS geofence verification to guarantee physical presence before attendance records are accepted and stored.

---

## 2. Scope
The scope of this project encompasses the development and architectural organization of the student-side mobile client for Android devices:
- **In Scope:**
  - Secure student login and credential validation.
  - Local caching of user profile and module data using Realm mobile database.
  - Weekly timetable navigation across 7-day schedules.
  - Real-time "NOW" dashboard highlighting active and upcoming classes.
  - GPS-based physical geofencing verifying proximity to classroom coordinates.
  - Attendance check-in submission transmitting verified logs to the backend API.
  - Robust client-side validation, error handling, and unit test suites.
- **Out of Scope:**
  - Instructor administrative dashboard and university ERP backend server administration (interfaced via RESTful API).
  - Biometric face recognition hardware integration (designated as an architectural roadmap extension).

---

## 3. Target Users
1. **University Students:** Primary end-users who consult lecture timetables, monitor session eligibility, and submit attendance verification from their mobile devices.
2. **Faculty & Instructors:** Primary beneficiaries who receive genuine, tamper-resistant attendance reports without sacrificing lecture time.
3. **Academic Administrators & Registrars:** Administrative personnel relying on accurate attendance data for course eligibility, compliance reports, and semester assessments.

---

## 4. High-Level Features
- **Credential Validation & Authentication:** Sanitizes input and authenticates students over REST API, storing authenticated session state locally.
- **Weekly Schedule & Timetable Browser:** Renders weekday schedules with lecture times, assigned professors, and assigned rooms.
- **Live Class Detection ("NOW" Engine):** Automatically detects ongoing and upcoming lectures, evaluating whether the session is open, late, or closed.
- **Geofence Proximity Verification:** Computes Haversine great-circle distance between the student's GPS location and the classroom coordinates, rejecting check-in attempts outside the allowed geofence radius.
- **Attendance Check-In & State Reporting:** Submits attendance status (`checked` or `late`) to remote database endpoints with confirmation feedback.
- **Fault-Tolerant Mobile Design:** Incorporates null safety, network timeout guards, and lifecycle cleanup to prevent memory leaks and unexpected crashes.

---

## 5. Functional Modules
1. **Authentication Module:** Manages login credentials, client-side syntax validation, remote authentication, and session persistence.
2. **Timetable & Module Browser Module:** Organizes course modules by weekday, rendering class times, rooms, and instructor details.
3. **Real-Time Schedule Tracker Module:** Evaluates current system time against module schedules to present live status and button controls.
4. **Geofence Verification Module:** Queries device location services, calculates distance to classroom coordinates, and evaluates boundary constraints.
5. **Attendance Check-In Module:** Executes check-in requests, triggers visual confirmation, and updates remote and local attendance states.

---

## 6. Expected Outcome
The resulting application provides an automated, reliable, and secure attendance verification platform. Students cannot mark attendance remotely; attendance is strictly tied to physical proximity within the classroom geofence. The system eliminates manual roll-calls, reduces administrative overhead, ensures authentic institutional records, and provides students with a dependable daily schedule companion.
