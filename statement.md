# Real-Time Attendance Monitoring System

## Problem Statement
Traditional attendance processes can be manual, time-consuming, and vulnerable to proxy attendance. This project provides a mobile attendance workflow in which a student authenticates and their location is checked before attendance is recorded.

## Scope
The project covers the student-side mobile application, authentication, timetable/module information, location-based verification, attendance check-in, and communication with a backend service/database. Teacher-side monitoring and backend administration are outside the primary scope of this mobile client.

## Target Users
- Students who need to record attendance.
- Instructors/administrators who use the connected attendance system to monitor records.

## High-Level Features
1. Student authentication and login.
2. Location-based attendance verification.
3. Attendance check-in workflow.
4. Timetable and current-module views.
5. Module management on the student side.
6. Backend API communication for attendance-related data.
7. Local data/model handling for student and module information.

## Functional Modules
### 1. Authentication Module
**Input:** Student login credentials.  
**Output:** Authenticated session or an error message.

### 2. Timetable and Module Module
**Input:** Student/module and timetable data.  
**Output:** Organized timetable and module information in the mobile UI.

### 3. Attendance Verification Module
**Input:** Current attendance request and device location.  
**Output:** Verification result and permission/denial to continue with check-in.

### 4. Attendance Check-In Module
**Input:** Authenticated student and successful verification.  
**Output:** Attendance request sent to the backend and corresponding status.

### 5. Backend Communication Module
**Input:** API requests and server responses.  
**Output:** Retrieved or submitted attendance/application data.

## Basic Workflow
Login -> Validate credentials -> Open student dashboard -> Select/current attendance -> Fetch and validate location -> Perform check-in -> Send attendance data to backend -> Display result.

## Non-Functional Requirements
1. **Usability:** The mobile interface should provide a straightforward attendance workflow with clear status messages.
2. **Security:** Authentication and location verification should be applied before attendance is accepted.
3. **Reliability:** Invalid credentials, unavailable location, and failed network requests should be handled without crashing the application.
4. **Maintainability:** Functionality is separated into activities, fragments, adapters, data-access classes, managers, and utility classes.
5. **Performance:** UI operations should remain responsive and network operations should be handled through the application's communication layer.
6. **Error Handling:** Validation failures and communication failures should produce controlled application feedback rather than silent failure.

## Technologies
- Java
- Android SDK
- Android application components
- GPS / Android Location Services
- PHP-based server integration
- MySQL backend database
- Gradle build system

## Academic Alignment
The project demonstrates modular programming, application architecture, validation, data handling, API communication, version control, and testing/verification concepts relevant to a software implementation project.

## Note on Project Development
This repository contains the supplied attendance-system codebase. Any additional features, modifications, testing, documentation, and design work should be clearly identified as the student's own contribution in the final academic submission.
