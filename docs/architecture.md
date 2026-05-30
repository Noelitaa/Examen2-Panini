# Arquitectura Técnica — Prueba de Concepto de Soporte Panini

## 1. Descripción general

Panini Support es una prueba de concepto para Android que gestiona tickets de soporte interno relacionados con la distribución de álbumes de la Copa Mundial de la FIFA 2026. La aplicación está desarrollada con Jetpack Compose, MVVM y una arquitectura en capas diseñada para una iteración rápida y una fácil transferencia a otros ingenieros.

--

## 2. Arquitectura: MVVM + Capas

Capa de interfaz de usuario → Pantallas componibles + ViewModels (observación de StateFlow)
Capa de dominio → TicketEventBus (comunicación basada en eventos)
Capa de datos → Repositorios → Datos simulados / futura API
Capa de red → Retrofit + OkHttp (cableado, listo para el backend)

Las capas están separadas de tal manera que cambiar la fuente de datos (simulado → backend real) solo requiere modificar `AppContainer.kt`; no es necesario cambiar nada en la interfaz de usuario ni en el ViewModel.

---
## 3. Estructura del paquete

```
com.moviles.paninisupport/
├── core/ AppConstants, UserMessages
├── data/
│ ├── mock/ MockData — datos realistas de Panini/FIFA 2026
│ ├── remote/ RetrofitClient, ApiService, DTOs
│ └── repository/ ApiResult, TicketRepository (interfaz + implementación simulada), AuthRepository
├── domain/ TicketEventBus — comunicación reactiva de eventos
├── features/ FeatureFlags
├── navigation/ AppDestinations, AppNavHost
└── ui/

├── components/ PriorityBadge, StatusBadge, TicketCard

├── pantallas/ inicio de sesión/, tickets/, detalle/, crear/

└── tema/ Colores, tipografía, tema
```

---

## 4. Comunicación basada en eventos (TicketEventBus)

### ¿Por qué SharedFlow?

La pantalla de lista y la pantalla de detalles son componentes independientes gestionadas por NavHost. Para mantenerlas sincronizadas sin recargas completas, la aplicación utiliza un bus de eventos basado en `SharedFlow`.

### Cómo funciona

CreateTicketScreen

└─ CreateTicketViewModel.createTicket()

└─ TicketEventBus.publish(TicketEvent.TicketCreated(ticket))

└─ TicketListViewModel.observeEvents() ← recibe el evento

└─ agrega el ticket a la lista, reordena por prioridad

└─ Actualización de StateFlow → LazyColumn se vuelve a renderizar automáticamente

El mismo patrón se aplica a los cambios de prioridad y estado:

TicketDetailViewModel.updatePriority()

└─ TicketEventBus.publish(TicketEvent.PriorityChanged(id, newPriority))

└─ TicketListViewModel recibe el evento

└─ actualiza el ticket en la lista Reordena por orden de prioridad

└─ CRÍTICO → ALTO → MEDIO → BAJO
```

### Eventos definidos

| Evento | Disparador | Efecto en la lista |

|---|---|---|

| `TicketCreated` | Nuevo ticket guardado | Añadido a la lista, ordenado por prioridad |

| `PriorityChanged` | Prioridad actualizada | Ticket reposicionado en la lista |

| `StatusChanged` | Estado actualizado | La insignia de estado se actualiza en su lugar |

### Notas técnicas

- `extraBufferCapacity = 10` evita la pérdida de eventos si el recolector se ralentiza momentáneamente.

- `tryEmit()` no suspende la ejecución, por lo que se puede llamar de forma segura desde cualquier ámbito de corrutina.

- El `TicketListViewModel` se suscribe al bus de eventos en su bloque `init` y mantiene la suscripción activa durante toda la vida útil del ViewModel.

---

## 5. Indicadores de características

Los indicadores de características se definen en `FeatureFlags.kt` como constantes booleanas en tiempo de compilación.

```kotlin
object FeatureFlags {

const val CREATE_TICKET_ENABLED = true // muestra/oculta el FAB

const val PRIORITY_UPDATE_ENABLED = true // muestra/oculta el menú desplegable de prioridad en la pantalla de detalles
}
```

### Indicadores en uso

| Indicador | Cuándo `false` |

|---|---|

| `CREATE_TICKET_ENABLED` | El FAB está oculto; los agentes no pueden abrir el formulario de creación |

| `PRIORITY_UPDATE_ENABLED` | El menú desplegable de prioridad está oculto en la pantalla de detalles |

### Cómo evolucionar

En un sistema de producción, reemplace los valores constantes con llamadas a Firebase Remote Config o un backend personalizado:

```kotlin
object FeatureFlags {

val CREATE_TICKET_ENABLED: Boolean

get() = RemoteConfig.getBoolean("create_ticket_enabled")
}
```

No es necesario modificar ningún otro archivo, ya que el uso de todos los flags se gestiona mediante `FeatureFlags`.

--

## 6. Capa de red

Retrofit está completamente configurado, aunque la prueba de concepto utiliza datos simulados. Los contratos de red se definen en:

- `ApiService.kt` — Interfaz Retrofit con todos los endpoints
- `RetrofitClient.kt` — Singleton de OkHttp + Retrofit
- `AppContainer.kt` — Punto de conexión de dependencias

### Cambio a un backend real

Modificar una línea en `AppContainer.kt`:

```kotlin
// Antes (simulador)
val ticketRepository: TicketRepository = MockTicketRepository()

// Después (backend real)
val ticketRepository: TicketRepository = RemoteTicketRepository(RetrofitClient.apiService)
```

`RemoteTicketRepository` implementaría la misma interfaz `TicketRepository` y llamaría a los métodos de `ApiService`; no se requieren cambios en el ViewModel ni en el código de la pantalla.

--

## 7. Gestión de estado

Cada pantalla tiene una clase de datos `UiState` correspondiente:

| Pantalla | Estado de la interfaz |

|---|---|

| Iniciar sesión | `isLoading`, `user`, `errorMessage` |

| Lista de tickets | `isLoading`, `tickets`, `errorMessage` |

| Detalles del ticket | `isLoading`, `ticket`, `errorMessage`, `successMessage` |

| Crear ticket