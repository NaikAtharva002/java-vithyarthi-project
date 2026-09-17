# Design and Testing Documentation

## 1. System Architecture

```mermaid
flowchart LR
    S[Student] --> A[Android Mobile Application]
    A --> AUTH[Authentication Layer]
    A --> ATT[Attendance & Check-In]
    A --> LOC[Location Verification]
    A --> TT[Timetable / Module UI]
    AUTH --> API[HTTP/API Communication Layer]
    ATT --> API
    LOC --> API
    API --> PHP[Server-side PHP Integration]
    PHP --> DB[(MySQL Database)]
```

## 2. Process Flow

```mermaid
flowchart TD
    START([Start]) --> LOGIN[Enter login credentials]
    LOGIN --> VALID{Credentials valid?}
    VALID -- No --> ERROR1[Show login error]
    ERROR1 --> LOGIN
    VALID -- Yes --> HOME[Open student dashboard]
    HOME --> CHECK[Start attendance check-in]
    CHECK --> GPS[Fetch device location]
    GPS --> INSIDE{Location valid?}
    INSIDE -- No --> ERROR2[Reject / show verification error]
    ERROR2 --> HOME
    INSIDE -- Yes --> SEND[Send attendance request]
    SEND --> SERVER[Backend processes request]
    SERVER --> RESULT{Request successful?}
    RESULT -- No --> ERROR3[Show communication/error status]
    RESULT -- Yes --> SUCCESS[Display attendance success]
    SUCCESS --> END([End])
```

## 3. Use Case Diagram

```mermaid
flowchart LR
    Student((Student))
    Login[Login]
    ViewModules[View / Manage Modules]
    ViewTimetable[View Timetable]
    CheckAttendance[Check In for Attendance]
    VerifyLocation[Verify Location]
    StoreAttendance[Store Attendance]

    Student --> Login
    Student --> ViewModules
    Student --> ViewTimetable
    Student --> CheckAttendance
    CheckAttendance --> VerifyLocation
    CheckAttendance --> StoreAttendance
```

## 4. Component/Class-Level Design

```mermaid
flowchart TD
    Activities[Activities\nLoginActivity / MainActivity / CheckInActivity / AddModuleActivity]
    Fragments[Fragments\nHome / Login / Now / Timetable / Verification]
    Adapters[Adapters\nModule / Timetable]
    DAO[DAO / Data Models\nUser / StudentModule]
    Managers[Managers\nHTTP / Context / TodayModule]
    API[ApiService]
    Backend[PHP Backend]
    Database[(MySQL)]

    Activities --> Fragments
    Fragments --> Adapters
    Fragments --> DAO
    Fragments --> Managers
    Managers --> API
    API --> Backend
    Backend --> Database
```

## 5. Sequence Diagram: Attendance Check-In

```mermaid
sequenceDiagram
    actor Student
    participant App as Android App
    participant Location as Location Service
    participant API as API Layer
    participant Server as Backend
    participant DB as MySQL

    Student->>App: Start attendance check-in
    App->>Location: Request current location
    Location-->>App: Return coordinates
    App->>App: Validate attendance location
    alt Location valid
        App->>API: Submit attendance request
        API->>Server: Send authenticated attendance data
        Server->>DB: Store attendance record
        DB-->>Server: Save result
        Server-->>API: Success response
        API-->>App: Attendance confirmed
        App-->>Student: Show success status
    else Location invalid
        App-->>Student: Show verification failure
    end
```

## 6. Data / ER Design

```mermaid
erDiagram
    USER ||--o{ ATTENDANCE : records
    MODULE ||--o{ ATTENDANCE : contains
    USER ||--o{ STUDENT_MODULE : enrolls
    MODULE ||--o{ STUDENT_MODULE : includes

    USER {
        int user_id PK
        string username
        string password
    }
    MODULE {
        int module_id PK
        string module_name
    }
    STUDENT_MODULE {
        int student_module_id PK
        int user_id FK
        int module_id FK
    }
    ATTENDANCE {
        int attendance_id PK
        int user_id FK
        int module_id FK
        string date_time
        string location_status
    }
```

### Logical Schema

| Entity | Purpose | Key fields |
|---|---|---|
| User | Stores student identity/authentication data | user_id, username |
| Module | Stores course/module information | module_id, module_name |
| StudentModule | Associates students with modules | student_module_id, user_id, module_id |
| Attendance | Stores attendance/check-in records | attendance_id, user_id, module_id, date_time, location_status |

> The exact production database schema depends on the connected backend implementation. The diagram above is the logical design to document the application's data relationships.

## 7. Testing Approach

### Functional Testing
- Verify valid login credentials are accepted.
- Verify invalid credentials are rejected with controlled feedback.
- Verify timetable/module screens load correctly.
- Verify attendance check-in starts correctly.
- Verify location verification blocks an invalid location.
- Verify a valid verification result proceeds to the attendance request.
- Verify server/API failure is handled without application crash.

### Validation Testing
- Empty login fields should not be silently accepted.
- Missing/unavailable location should produce an understandable status.
- Network/API failure should be handled gracefully.

### Unit / Instrumentation Tests
The project includes Android test source files. Tests should be executed after importing the project into a compatible Android Studio/Gradle environment, because the supplied build configuration is an older Android project.

## 8. Design Decisions and Rationale

- **Android + Java:** Provides a native mobile environment for student attendance operations.
- **Location verification:** Adds a physical-presence check before attendance submission.
- **Layered organization:** Activities/fragments handle UI flow while adapters, DAO/model classes, managers, and API services separate responsibilities.
- **Backend database:** Allows attendance data to be persisted and accessed by the connected attendance system.
- **Git:** Provides version history and a reproducible project repository.
