# Real-Time Attendance Monitoring System

**Student Details:**
- **Name:** Atharva Naik
- **Registration No:** 25BAI10771
- **Email:** atharva.25bai10771@gmail.com

**Academic Project — Mobile Attendance Verification System with Geofencing**

---

## 1. Project Title
**Real-Time Attendance Monitoring System (Student Check-In Client)**

---

## 2. Project Overview
The **Real-Time Attendance Monitoring System** is a native Java Android application designed to modernize and secure student attendance management in academic institutions. By combining user authentication with GPS-based geofencing and real-time schedule tracking, the application prevents common attendance malpractice (such as proxy attendance and manual sign-in tampering). Students can view their daily timetables, see their active lectures, and check in only when physically present within the permitted classroom geofence.

---

## 3. Problem Statement
In traditional classroom and lecture environments, attendance is typically marked through physical paper sheets, roll-calls, or manual instructor logs. These methods exhibit severe vulnerabilities:
- **Proxy Attendance**: Students can easily sign or answer on behalf of absent peers.
- **Time Inefficiency**: Taking roll-call for large classes consumes significant instruction time.
- **Data Inconsistency**: Manual records are error-prone, easily lost or altered, and require tedious transcription into central academic management systems.
- **Lack of Physical Verification**: Standard web forms or simple apps without location enforcement allow students to register attendance from dormitories or outside the campus.

---

## 4. Objectives
- Eliminate attendance fraud and proxy marking using physical location verification.
- Provide a clean, intuitive mobile dashboard for students to inspect their schedules and upcoming classes.
- Automate attendance state transitions (Upcoming, Active Check-In, Late Attendance, Closed) based on class timetables.
- Synchronize attendance logs with a remote relational database and maintain local persistence via Realm database.
- Offer controlled error handling and input validation for secure mobile operations.

---

## 5. Target Users
- **Students**: Mobile users who access schedules, track daily lectures, and submit check-in verification during designated attendance windows.
- **Instructors / Faculty**: Beneficiaries of authentic, location-verified attendance reports and automated logs.
- **Academic Administration**: Personnel who require reliable attendance records for eligibility, compliance, and academic audits.

---

## 6. Major Features

### A. Existing Core Features
1. **Student Authentication**: Secure login screen authenticating student credentials against remote endpoints and storing session state in `SharedPreferences` and local `Realm` storage.
2. **Weekly Timetable & Day Views**: Seven-day tabbed timetable (Monday through Sunday) displaying registered modules, room numbers, lecture times, and instructor details.
3. **Real-Time Status Screen ("NOW")**: Dynamic screen showing the current or next upcoming lecture, start/end times, instructor, classroom, and live check-in eligibility.
4. **Interactive Google Maps Geofence Visualization**: Displays the target classroom on a map and computes a spatial bounding box / radius around the room.
5. **Session Check-In & Status Transitions**: Supports on-time attendance (`checked`), late attendance (`late`), and prevents check-in outside authorized windows.
6. **Local Database Synchronization**: Uses Realm database (`landtanin.realm`) to store and query student modules offline.

### B. Newly Added & Improved Features (Cleanup & Engineering Improvements)
1. **Input Validation Suite (`ValidationUtils`)**:
   - RFC-compliant email syntax validation.
   - Password minimum length verification.
   - Non-empty field guards and input focus feedback on the login screen.
2. **Pure-Java Geofencing Engine (`GeofenceUtils`)**:
   - Haversine geodesic distance calculation between device GPS and target lecture hall.
   - Informative student feedback showing exact distance away (e.g. `120m away` or `1.4 km away`) when outside check-in boundaries.
3. **Session Timing Engine (`AttendanceTimeUtils`)**:
   - Evaluates attendance milestones (`BEFORE_START`, `ACTIVE`, `LATE`, `ENDED`).
   - Clean formatting for time ranges.
4. **Lifecycle & Memory Leak Prevention**:
   - Bound background timer in `FragmentNow` to fragment lifecycle (`onDestroyView`), stopping runaway polling threads and avoiding `NullPointerException` on detached views.
5. **Null Safety & Robust Error Handling**:
   - Guarded intent extras and database query results in `CheckInActivity` and `FragmentHome`.
   - Replaced silent log-only network errors with user-visible feedback toasts.
6. **Automated Unit Testing Suite**:
   - 22 comprehensive JUnit 4 tests covering credential validation, Haversine geofencing, and timetable session transitions.

---

## 7. Functional Modules

### Module 1: Authentication & Session Management
- **Input**: Student Email (`email`), Password (`password`).
- **Processing**: Format validation via `ValidationUtils`, POST request to `studentLogin/index.php`, session caching in `SharedPreferences`, user model persistence in `Realm`.
- **Output**: Navigation to `MainActivity` on success; validation/error toasts on failure.

### Module 2: Timetable & Schedule Management
- **Input**: Authenticated Student ID (`student_id`).
- **Processing**: GET request to `studentModuleGet.php`, local persistence in Realm (`StudentModuleDao`), weekly filtering by weekday (`Mon`, `Tue`, `Wed`, etc.) across fragment pagers.
- **Output**: Tabbed weekday schedule and real-time "NOW" card showing current class details.

### Module 3: Location Verification & Check-In
- **Input**: Device GPS coordinates (`LocationServices.FusedLocationApi`), module target coordinates (`LocLat`, `LocLng`).
- **Processing**: Geodesic distance calculation (`GeofenceUtils`), map marker rendering, popup verification dialog, POST request to `studentModuleUpdate.php` with attendance status (`checked` or `late`).
- **Output**: Confirmation popup dialog, updated button state ("checked / you are in"), attendance status logged to backend.

---

## 8. Non-Functional Requirements
- **Security**: Pre-network input validation; API communication via Retrofit; verification enforced before check-in submission.
- **Usability**: Clean Android Material Design UI with color-coded status buttons (Green = Active, Grey = Waiting, Red = Late, Indigo = Checked).
- **Reliability**: Graceful handling of location permission denials, disabled GPS, missing modules, and network timeouts.
- **Performance**: Asynchronous network I/O powered by RxJava schedulers; off-main-thread geofence math.
- **Maintainability**: Clear architectural separation between Activities, Fragments, Adapters, DAOs, Managers, and Utility helpers.

---

## 9. Technologies Used
- **Language**: Java (JDK 7/8 source compatibility)
- **Platform**: Android SDK (Target SDK 25 / Min SDK 15)
- **UI Framework**: Android Support Library (AppCompat, RecyclerView, Design, DataBinding)
- **Geospatial & Maps**: Google Play Services (Maps & Location `10.2.1`), Google Maps Android API Utility Library (`0.3.4`)
- **Networking & Serialization**: Square Retrofit 2.2.0, OkHttp 3, Google Gson 2.2.0
- **Asynchronous Reactive Stream**: ReactiveX RxJava 1.1.8, RxAndroid 1.2.1
- **Local Persistence**: Realm Mobile Database for Android (`io.realm:realm-gradle-plugin:3.1.1`)
- **Testing**: JUnit 4.12, AndroidJUnitRunner

---

## 10. System Workflow
```
+-------------------------------------------------------------+
|                      1. Student Login                       |
|   Enter Email & Password -> Client-side Validation Checks   |
+------------------------------+------------------------------+
                               | Valid
                               v
+-------------------------------------------------------------+
|              2. Authentication & Data Sync                  |
|    POST /studentLogin -> Save User & Modules into Realm     |
+------------------------------+------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|               3. Dashboard & Schedule View                  |
|   Displays Timetable & identifies Current Active Module     |
+------------------------------+------------------------------+
                               | Active Module Selected
                               v
+-------------------------------------------------------------+
|              4. GPS Geofence Verification                   |
|  Acquire Device Location -> Compare with Class Coordinates  |
+------------------------------+------------------------------+
                               | Inside Geofence
                               v
+-------------------------------------------------------------+
|               5. Attendance Check-In                        |
|  Confirm Check-In -> POST /studentModuleUpdate -> Success   |
+-------------------------------------------------------------+
```

---

## 11. Architecture
The project follows a modular, layered Android architecture:
- **Presentation Layer**: Activities (`LoginActivity`, `MainActivity`, `CheckInActivity`, `AddModuleActivity`) hosting modular Fragments (`FragmentLogin`, `FragmentHome`, `FragmentNow`, `FragmentTimeTable`, Day Fragments).
- **Domain & Business Logic Layer**: Utility engines (`ValidationUtils`, `GeofenceUtils`, `AttendanceTimeUtils`) and Managers (`TodayModule`, `Contextor`).
- **Data Access Layer**: Realm Data Access Objects (`User`, `StudentModuleDao`, `StudentModuleCollectionDao`).
- **Network / Remote Layer**: Retrofit API interface (`ApiService`), OkHttp HTTP client, and `HttpManager` logging interceptors.

---

## 12. Project Structure
```
F:\real time attendance monitor/
├── app/
│   ├── build.gradle                   # App module build configuration
│   ├── proguard-rules.pro             # ProGuard code shrinking rules
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml    # App permissions, activities, metadata
│       │   ├── java/com/landtanin/studentattendancecheck/
│       │   │   ├── MainApplication.java
│       │   │   ├── activity/          # LoginActivity, MainActivity, CheckInActivity...
│       │   │   ├── fragment/          # FragmentHome, FragmentNow, FragmentLogin...
│       │   │   │   ├── day/           # MondayFragment, TuesdayFragment...
│       │   │   │   └── verifying/     # LocationFragment, FaceRecogFragment
│       │   │   ├── adapter/           # TimeTableListAdapter, AddModuleAdapter...
│       │   │   ├── dao/               # User, StudentModuleDao, StudentModuleCollectionDao
│       │   │   ├── manager/           # HttpManager, TodayModule, Contextor
│       │   │   │   └── http/          # ApiService
│       │   │   └── util/              # ValidationUtils, GeofenceUtils, AttendanceTimeUtils
│       │   └── res/                   # Layouts, drawables (hdpi to xxxhdpi), values, menus
│       └── test/java/com/landtanin/studentattendancecheck/
│           ├── ValidationUtilsTest.java
│           ├── GeofenceUtilsTest.java
│           ├── AttendanceTimeUtilsTest.java
│           └── ExampleUnitTest.java
├── docs/
│   └── design-and-testing.md          # Comprehensive architecture & design document
├── gradle/wrapper/                    # Gradle wrapper binaries & configuration
├── build.gradle                       # Root Gradle project build script
├── gradle.properties                  # JVM memory and Gradle properties
├── gradlew / gradlew.bat              # Gradle wrapper execution scripts
├── settings.gradle                    # Project module inclusion
├── statement.md                       # Academic project statement
└── README.md                          # Project documentation
```

---

## 13. Installation Requirements
To import, inspect, or build this project:
- **Operating System**: Windows, macOS, or Linux.
- **IDE**: Android Studio (Android Studio Hedgehog, Giraffe, or earlier with Android SDK Build Tools 25.0.2).
- **Java Development Kit (JDK)**: JDK 8 is required for the bundled Gradle 3.3 wrapper.
- **Android SDK Components**:
  - Android SDK Platform 25 (Nougat 7.1.1)
  - Android SDK Build-Tools 25.0.2
  - Google Play Services repository and support repository

---

## 14. How to Run
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/NaikAtharva002/java-vithyarthi-project.git
   ```
2. **Open in Android Studio**:
   - Launch Android Studio and select **File -> Open...**
   - Choose the project directory.
3. **Configure Project JDK**:
   - Ensure the Gradle JDK in Android Studio is set to **JDK 8 / 1.8** (`Settings -> Build, Execution, Deployment -> Build Tools -> Gradle -> Gradle JDK`).
4. **Sync Gradle**:
   - Allow Android Studio to sync dependencies and generate data binding classes.
5. **Run on Emulator or Device**:
   - Start an Android Emulator with Google Play Services enabled (API 21+ recommended).
   - Click **Run -> Run 'app'**.
6. **Demo Credentials**:
   - `user`: `johndoe@johndoe.com` | `pass`: `johndoe`
   - `user`: `harrypotter@harrypotter.com` | `pass`: `harrypotter`

---

## 15. Testing
The project includes both automated unit tests and manual hardware verification procedures:

### Automated Unit Tests
The test suite in `app/src/test/java/` contains 22 automated test cases executing against the JVM:
- **`ValidationUtilsTest`**: Validates RFC email patterns, handles null/whitespace, tests boundary password lengths, and validates student IDs.
- **`GeofenceUtilsTest`**: Evaluates coordinate validity, distance calculations between known landmarks, and within-radius tolerance checks.
- **`AttendanceTimeUtilsTest`**: Verifies status transitions across timetable milestones (`BEFORE_START`, `ACTIVE`, `LATE`, `ENDED`).

To execute unit tests in Android Studio:
- Right-click `app/src/test/java` -> Select **Run 'Tests in studentattendancecheck'**.

### Manual Hardware & Integration Testing
Certain features require physical hardware or emulator instrumentation:
- **GPS Location Provider**: Requires GPS sensor or Emulator Location Mocking (`Extended Controls -> Location`).
- **Google Play Services Maps**: Requires valid Google Play Services installed on device.
- **Remote Backend API**: Live check-in calls require the target backend server to be reachable.

---

## 16. Known Limitations
- **Legacy Gradle & Build Tools**: The build scripts target Gradle 3.3 and Android Gradle Plugin 2.3.3. Running Gradle from modern terminal environments with JDK 17+ or JDK 26 fails due to legacy Gradle Java version parsing. Building requires JDK 8 in Android Studio.
- **External Backend Availability**: The remote Bitnami backend endpoint (`http://landtanin.bitnamiapp.com/api/`) is a legacy demo host. When the server is unreachable, the app's newly improved error handling surfaces friendly network alerts to the user.
- **GPS Indoor Accuracy**: Standard mobile GPS accuracy can degrade inside dense concrete campus buildings; Wi-Fi / cellular assisted positioning is recommended.

---

## 17. Future Enhancements
- **Facial Recognition Attendance Verification**: Integrate on-device ML (e.g. ML Kit Face Detection / TensorFlow Lite) to complement GPS with biometric confirmation. (`FaceRecogFragment` exists as an initial UI placeholder for this capability).
- **Dynamic BLE / Bluetooth Beacon Verification**: Use classroom Bluetooth Low Energy beacons for micro-location indoors where GPS signal is weak.
- **Offline Attendance Queue**: Queue check-in requests locally in Realm and sync automatically when network connectivity is restored.
- **Modernized Android Architecture**: Migrate to modern Android Jetpack (AndroidX, Navigation Component, Room DB, Kotlin Coroutines, ViewModel/LiveData).

---

## 18. References & Attributions
- Original base project concepts and templates created by **landtanin** and **nuuneoi**. Original comments and attributions have been preserved across all source files.
- Academic refactoring, input validation, Haversine geofencing utilities, lifecycle leak fixes, automated unit test suites, and documentation prepared for the **VITyarthi Academic Submission**.
