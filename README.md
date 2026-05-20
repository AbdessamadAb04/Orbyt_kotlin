# Orbyt 🚀

Orbyt is a premium, all-in-one productivity and personal management application built for Android. It combines task management, habit tracking, financial oversight, and goal setting into a single, cohesive experience with a focus on a "clean, calm, and premium" design using **Jetpack Compose** and **Material 3**.

## 🌟 Concept

The core philosophy of Orbyt is to reduce cognitive load by centralizing all aspects of personal organization. Orbyt follows a "minimalist yet powerful" approach, using French localization to cater to its primary audience while maintaining a high-end, professional aesthetic.

## ✨ Key Features

- **📱 Modular Dashboard**: A "at-a-glance" view of your day, featuring widgets for tasks, habits, agenda, finances, and goals.
- **📅 Premium Schedule**: A refined timeline view of appointments combined with D-Day countdowns for significant events.
- **💰 Financial Tracking**: Custom canvas-based visualizations for monthly balance, income, and expenses.
- **🔄 Habit Formation**: Atomic habit tracking with streak visualization and daily progress logs.
- **✅ Task Management**: Simple, priority-based task organization with a focus on "getting things done."
- **🎯 Goal Setting**: Structured goals with step-by-step progress tracking.
- **📝 Notes & Thoughts**: Capture ideas quickly in a dedicated notes module.
- **👤 Profile & Settings**: Minimalist user management and application customization.
- **🎉 Seamless Onboarding**: A clean, distraction-free Welcome screen to set the tone for productivity.

## 🛠 Project Structure

The project follows a modern Android architecture (MVVM), separating data concerns from UI logic and utilizing Clean Architecture principles.

### 📦 Source Code Organization (app/src/main/java)

- **`com.abdessamad.orbyt.ui`**: The UI layer built entirely with Jetpack Compose.
  - `welcome`: Distraction-free entry screen.
  - `dashboard`: The modular hub of the application.
  - `agenda`: Schedule management and countdowns.
  - `finance`: Budget tracking and visual charts.
  - `tasks`, `habits`, `goals` & `notes`: Core productivity tracking modules.
  - `profile`: User settings and app information.
  - `navigation`: Orchestrates the navigation graph and transitions.
  - `theme`: Material 3 custom color schemes, typography, and shapes.
  - `viewmodel`: Business logic and state management (MVVM).
- **`com.abdessamad.orbyt.data`**: The data layer handling persistence.
  - `local/entity`: Room database entities (Appointment, Habit, Task, Transaction, etc.).
  - `local/dao`: Data Access Objects for database operations.
  - `repository`: Repository pattern implementation for data abstraction.

## 🚀 Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/) (2.0.21)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Database**: [Room](https://developer.android.com/training/data-storage/room) (2.6.1) for offline-first persistence.
- **Navigation**: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Asynchronous**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Dependency Management**: [Gradle Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html) (libs.versions.toml)

## 🎨 Design System

Orbyt utilizes a custom design system characterized by:
- **Calm Palette**: Deep blues, nuanced grays, and soft semantic colors (Success Green, Danger Red, Warning Amber).
- **Typography**: High-contrast, clean hierarchy using Material 3 `displayLarge` for headers.
- **Visual Style**:
    - **Dashboard Containers**: Unified `DashboardModuleContainer` with 24.dp rounded corners and subtle background alpha.
    - **Modular Design**: Reusable UI components for consistent look and feel across all features.

## 🛠 Installation & Setup

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/orbyt.git
    ```
2.  **Open in Android Studio**:
    Open the project in Android Studio (Ladybug or later recommended).
3.  **Sync Gradle**:
    Let the project sync and download dependencies.
4.  **Run**:
    Select an emulator or physical device and click **Run**.

---
*Developed with focus on productivity and design excellence.*
