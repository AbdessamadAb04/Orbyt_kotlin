# Orbyt Flutter Re-engineering Specifications 🚀

This document outlines the technical architecture and UI/UX patterns of the Orbyt Android project (Jetpack Compose) to guide its reconstruction in **Flutter**.

## 🏗️ Data Architecture (Backend Resolution)

The project currently uses **Room** for local persistence. In Flutter, the recommended equivalent is **sqflite** (raw SQL) or **Drift** (reactive persistence library).

### 🗄️ Database: `orbyt_database`
- **Engine**: SQLite.
- **Entities & Schema Mapping**:

| Entity | Attributes | Flutter Model Hint |
| :--- | :--- | :--- |
| **Task** | `id`, `title`, `priority` (Enum), `status` (Enum), `dueDate` (Timestamp), `projectId` (FK), `createdAt`, `isArchived` | Use `Freezed` for immutable models. |
| **Habit** | `id`, `name`, `emoji`, `isArchived`, `createdAt` | Habit tracking follows an atomic pattern. |
| **HabitLog** | `id`, `habitId` (FK), `date` (Timestamp), `isCompleted` | Stores daily execution history. |
| **Transaction**| `id`, `amount`, `type` (INCOME/EXPENSE), `category` (Enum), `note`, `date`, `isArchived` | Used for custom canvas visualizations. |
| **Goal** | `id`, `title`, `description`, `icon`, `targetValue`, `currentValue`, `isArchived` | Tracks progress against milestones. |
| **GoalStep** | `id`, `goalId` (FK), `stepTitle`, `isCompleted` | Granular breakdown of goals. |
| **Appointment**| `id`, `title`, `location`, `dateTime`, `isAllDay` | Central for the Agenda/Schedule view. |
| **Note** | `id`, `title`, `content`, `color`, `createdAt` | Rapid ideation capture. |

### 🔄 State Management Strategy
The current Android project uses **MVVM** with `ViewModel`. In Flutter, implement this using:
- **Provider** or **Riverpod**: For dependency injection and state observation.
- **Repository Pattern**: Abstract data sources (Local SQL) behind repositories to allow for future remote sync (Firebase/Supabase).

---

## 🎨 UI Layout & User Experience

Orbyt follows a "Clean, Calm, and Premium" design language.

### 📍 Navigation Structure
Implement a **4-Tab Bottom Navigation Bar** with the following routes:

1.  **Dashboard (`/dashboard`)**: The central hub.
2.  **Tasks (`/tasks`)**: List and board views for task management.
3.  **Agenda (`/agenda`)**: Timeline and countdown views.
4.  **Finance (`/finance`)**: Visual expense/income reports.

*Secondary Routes*: `/welcome` (Onboarding), `/profile` (Settings), `/habits`, `/goals`, `/notes`.

### 🧩 UI Components & Patterns

#### 1. The Dashboard Module Container
- **Android**: `DashboardModuleContainer` (24.dp corners, 3% alpha background).
- **Flutter Concept**: 
    ```dart
    Container(
      decoration: BoxDecoration(
        color: Theme.of(context).colorScheme.surface.withAlpha(8), // Subtle 3%
        borderRadius: BorderRadius.circular(24),
      ),
      padding: EdgeInsets.all(16),
      child: Column( /* ... */ ),
    );
    ```

#### 2. Visual Identity (Theme)
- **Primary Colors**: `OrbytBlue` (0xFF1A56DB), `AccentIndigo` (0xFF6366F1).
- **Backgrounds**: `BackgroundLight` (0xFFF8FAFC) / `BackgroundDark` (0xFF0F172A).
- **Typography**: High contrast hierarchy. Use `Material 3` TextThemes (`displayLarge` for module headers).

#### 3. Custom Visualizations (Finance & Habits)
- **Finance**: Monthly balance charts using `CustomPainter` (or `fl_chart`).
- **Habits**: Heatmaps and streak visualizations using a grid-based approach.

---

## 🛠️ Flutter Development Checklist for AI

- [ ] **Dependencies**: Add `sqflite`/`drift`, `riverpod`, `go_router`, and `google_fonts`.
- [ ] **Theme**: Create an `OrbytTheme` class supporting both Light and Dark modes using the hex codes provided above.
- [ ] **Models**: Define Clean Architecture layers: `Domain` (Entities), `Data` (Repositories/Mappers), `Presentation` (Riverpod States/UI).
- [ ] **Localization**: Prioritize French (as per original logic) using `flutter_localizations`.

---
*This document serves as the bridging specification for the AI transition from Kotlin/Compose to Dart/Flutter.*