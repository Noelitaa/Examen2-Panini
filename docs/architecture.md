# Technical Architecture — Panini Support PoC

## 1. Overview

Panini Support is an Android proof-of-concept for managing internal support tickets related to the FIFA World Cup 2026 album distribution. The app is built with Jetpack Compose, MVVM, and a layered architecture designed for fast iteration and easy handoff to other engineers.

---

## 2. Architecture: MVVM + Layered

```
UI Layer       →  Composable screens + ViewModels (observe StateFlow)
Domain Layer   →  TicketEventBus (event-based communication)
Data Layer     →  Repositories → Mock data / future API
Network Layer  →  Retrofit + OkHttp (wired, backend-ready)
```

The layers are separated so that swapping the data source (mock → real backend) requires changing only `AppContainer.kt` — nothing in the UI or ViewModel needs to change.

---

## 3. Package Structure

```
com.moviles.paninisupport/
├── core/           AppConstants, UserMessages
├── data/
│   ├── mock/       MockData — realistic Panini/FIFA 2026 data
│   ├── remote/     RetrofitClient, ApiService, DTOs
│   └── repository/ ApiResult, TicketRepository (interface + mock impl), AuthRepository
├── domain/         TicketEventBus — reactive event communication
├── features/       FeatureFlags
├── navigation/     AppDestinations, AppNavHost
└── ui/
    ├── components/ PriorityBadge, StatusBadge, TicketCard
    ├── screens/    login/, tickets/, detail/, create/
    └── theme/      Colors, Typography, Theme
```

---

## 4. Event-Based Communication (TicketEventBus)

### Why SharedFlow?

The list screen and the detail screen are independent composables managed by the NavHost. To keep them in sync without full reloads, the app uses a `SharedFlow`-based event bus.

### How it works

```
CreateTicketScreen
  └─ CreateTicketViewModel.createTicket()
        └─ TicketEventBus.publish(TicketEvent.TicketCreated(ticket))
              └─ TicketListViewModel.observeEvents()  ← receives the event
                    └─ adds ticket to list, re-sorts by priority
                          └─ StateFlow update → LazyColumn re-renders automatically
```

The same pattern applies to priority changes and status changes:

```
TicketDetailViewModel.updatePriority()
  └─ TicketEventBus.publish(TicketEvent.PriorityChanged(id, newPriority))
        └─ TicketListViewModel receives event
              └─ updates ticket in list, re-sorts by priority order
                    └─ CRITICAL → HIGH → MEDIUM → LOW
```

### Events defined

| Event | Trigger | Effect on list |
|---|---|---|
| `TicketCreated` | New ticket saved | Added to list, sorted by priority |
| `PriorityChanged` | Priority updated in detail | Ticket re-positioned in list |
| `StatusChanged` | Status updated in detail | Status badge updates in-place |

### Technical notes

- `extraBufferCapacity = 10` prevents event loss if the collector is momentarily slow.
- `tryEmit()` is non-suspending, safe to call from any coroutine scope.
- The `TicketListViewModel` subscribes to the event bus in its `init` block and keeps the subscription alive for the lifetime of the ViewModel.

---

## 5. Feature Flags

Feature flags are defined in `FeatureFlags.kt` as compile-time boolean constants.

```kotlin
object FeatureFlags {
    const val CREATE_TICKET_ENABLED = true   // shows/hides the FAB
    const val PRIORITY_UPDATE_ENABLED = true // shows/hides priority dropdown in detail
}
```

### Flags in use

| Flag | When `false` |
|---|---|
| `CREATE_TICKET_ENABLED` | FAB is hidden; agents cannot open the creation form |
| `PRIORITY_UPDATE_ENABLED` | Priority dropdown is hidden in the detail screen |

### How to evolve

In a production system, replace the constant values with calls to Firebase Remote Config or a custom backend:

```kotlin
object FeatureFlags {
    val CREATE_TICKET_ENABLED: Boolean
        get() = RemoteConfig.getBoolean("create_ticket_enabled")
}
```

No other file needs to change because all flag usage is already mediated through `FeatureFlags`.

---

## 6. Networking Layer

Retrofit is fully wired even though the PoC uses mock data. The networking contracts are defined in:

- `ApiService.kt` — Retrofit interface with all endpoints
- `RetrofitClient.kt` — OkHttp + Retrofit singleton
- `AppContainer.kt` — dependency wiring point

### Switching to a real backend

Change one line in `AppContainer.kt`:

```kotlin
// Before (mock)
val ticketRepository: TicketRepository = MockTicketRepository()

// After (real backend)
val ticketRepository: TicketRepository = RemoteTicketRepository(RetrofitClient.apiService)
```

`RemoteTicketRepository` would implement the same `TicketRepository` interface and call `ApiService` methods — no ViewModel or screen code changes needed.

---

## 7. State Management

Each screen has a corresponding `UiState` data class:

| Screen | UiState |
|---|---|
| Login | `isLoading`, `user`, `errorMessage` |
| Ticket List | `isLoading`, `tickets`, `errorMessage` |
| Ticket Detail | `isLoading`, `ticket`, `errorMessage`, `successMessage` |
| Create Ticket | `isLoading`, `success`, `errorMessage` |

States are exposed as `StateFlow` from the ViewModel and collected with `collectAsStateWithLifecycle()` in composables to respect the Android lifecycle.

---

## 8. Mock Data

`MockData.kt` contains 10 realistic tickets covering all categories and priorities. Data represents real Panini/FIFA 2026 scenarios: distribution delays, inventory shortages, quality defects, logistics losses, and provider contract issues.

---

## 9. API Contracts

Defined in `/contracts/tickets-api.yaml` (OpenAPI 3.0.3). Contracts cover all five endpoints the mobile app would consume:

- `POST /auth/login`
- `GET /tickets`
- `POST /tickets`
- `GET /tickets/{id}`
- `PATCH /tickets/{id}/status`
- `PATCH /tickets/{id}/priority`

---

## 10. Future Evolution

- **DI framework**: Replace `AppContainer` with Hilt when the project grows beyond 2–3 developers.
- **Remote Feature Flags**: Wire `FeatureFlags` to Firebase Remote Config without changing call sites.
- **Pagination**: `getTickets()` can accept `page` and `pageSize` parameters — the `LazyColumn` in the list screen is already prepared for incremental loading.
- **Offline support**: Add a Room database layer between the repository and the API, following the same pattern used in the unaroom-android reference project.
