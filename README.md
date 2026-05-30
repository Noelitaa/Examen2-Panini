# Panini Support — FIFA World Cup 2026

Internal mobile support ticket system for Panini's FIFA World Cup 2026 album distribution operations.

## Description

This proof-of-concept Android application centralizes support ticket management for issues related to providers, inventory, logistics, and distribution of the FIFA 2026 sticker album. It replaces email chains and spreadsheets with a structured, trackable ticket workflow.

## Repository Structure

```
/app          Android project (Jetpack Compose, MVVM)
/contracts    API contracts in OpenAPI 3.0 YAML format
/docs         Technical architecture documentation
/video        Demo video link
README.md     This file
```

## Getting Started

### Requirements

- Android Studio Meerkat (2024.3.1) or later
- JDK 11
- Android SDK 36
- A physical device or emulator running Android 7.0 (API 24) or higher

### Run the app

1. Open Android Studio
2. Open the `/app` folder as the project root
3. Wait for Gradle sync to complete
4. Run the `:app` configuration on your device or emulator

### Login credentials

The PoC uses simulated authentication. Any valid email format and a password of 4+ characters will work.

Example:
- Email: `agent@panini.cr`
- Password: `panini2026`

## Technologies

| Category | Technology |
|---|---|
| UI | Jetpack Compose, Material3 |
| Architecture | MVVM, ViewModel, StateFlow |
| Navigation | Navigation Compose |
| Networking | Retrofit 2, OkHttp, Gson |
| Async | Kotlin Coroutines |
| Event bus | SharedFlow (TicketEventBus) |
| Data | Mock data (backend-ready structure) |
| Build | Gradle KTS, Version Catalogs |

## Key Technical Decisions

**Event-based communication via SharedFlow**: The `TicketEventBus` singleton lets screens react to ticket changes (creation, priority update, status update) without polling or full reloads. The ticket list re-sorts automatically whenever a priority changes in the detail screen.

**Feature Flags in `FeatureFlags.kt`**: Two flags control ticket creation and priority updates. Toggling a flag disables the corresponding UI element instantly. The flags are ready to be wired to a remote configuration service (e.g. Firebase Remote Config) without changing any call site.

**Backend-ready architecture**: The `TicketRepository` interface, `ApiService`, and `RetrofitClient` are fully defined. Switching from mock to real backend requires changing one line in `AppContainer.kt`.

## Notes for Other Developers

- Mock data lives in `data/mock/MockData.kt` — 10 realistic tickets covering all priorities, statuses, providers, and categories.
- API contracts are in `/contracts/tickets-api.yaml` — review these before starting backend development.
- See `/docs/architecture.md` for a full explanation of the event bus, feature flags, and how to evolve the project.
