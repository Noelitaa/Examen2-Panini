# Soporte Panini — Copa Mundial de la FIFA 2026

Sistema interno de gestión de tickets de soporte móvil para las operaciones de distribución del álbum de cromos de la Copa Mundial de la FIFA 2026 de Panini.

## Descripción

Esta aplicación Android, en fase de prueba de concepto, centraliza la gestión de tickets de soporte para problemas relacionados con proveedores, inventario, logística y distribución del álbum de cromos de la FIFA 2026. Sustituye las cadenas de correos electrónicos y las hojas de cálculo por un flujo de trabajo estructurado y con seguimiento de tickets.

## Estructura del repositorio

```
/app Proyecto Android (Jetpack Compose, MVVM)
/contracts Contratos API en formato YAML OpenAPI 3.0
/docs Documentación de la arquitectura técnica
/video Enlace al vídeo de demostración
README.md Este archivo
```

## Primeros pasos

### Requisitos

- Android Studio Meerkat (2024.3.1) o posterior
- JDK 11
- Android SDK 36
- Un dispositivo físico o emulador con Android 7.0 (API 24) o superior

### Ejecutar la aplicación

1. Abre Android Studio
2. Abre la carpeta `/app` como raíz del proyecto
3. Espera a que finalice la sincronización de Gradle
4. Ejecuta la configuración `:app` en tu dispositivo o emulador

### Credenciales de inicio de sesión

La prueba de concepto utiliza autenticación simulada. Cualquier formato de correo electrónico válido y una contraseña de al menos 4 caracteres funcionarán.

Ejemplo:
- Correo electrónico: `agent@panini.cr`
- Contraseña: `panini2026`

## Tecnologías

| Categoría | Tecnología |

|---|---|

| Interfaz de usuario | Jetpack Compose, Material3 |

| Arquitectura | MVVM, ViewModel, StateFlow |

| Navegación | Navigation Compose |

| Redes | Retrofit 2, OkHttp, Gson |

| Asíncrono | Corrutinas de Kotlin |

| Bus de eventos | SharedFlow (TicketEventBus) |

| Datos | Datos simulados (estructura lista para el backend) |

| Compilación | Gradle KTS, Catálogos de versiones |

## Decisiones técnicas clave

**Comunicación basada en eventos mediante SharedFlow**: El singleton `TicketEventBus` permite que las pantallas reaccionen a los cambios en los tickets (creación, actualización de prioridad, actualización de estado) sin sondeo ni recargas completas. La lista de tickets se reordena automáticamente cuando cambia la prioridad en la pantalla de detalles.

**Indicadores de características en `FeatureFlags.kt`**: Dos indicadores controlan la creación de tickets y las actualizaciones de prioridad. Al activar o desactivar un indicador, se desactiva instantáneamente el elemento de la interfaz de usuario correspondiente. Los indicadores están listos para integrarse con un servicio de configuración remota (por ejemplo, Firebase Remote Config) sin necesidad de modificar el punto de llamada.

**Arquitectura lista para el backend**: La interfaz `TicketRepository`, `ApiService` y `RetrofitClient` están completamente definidas. Para cambiar de un backend simulado a uno real, basta con modificar una línea en `AppContainer.kt`.

## Notas para otros desarrolladores

- Los datos simulados se encuentran en `data/mock/MockData.kt`: 10 tickets realistas que abarcan todas las prioridades, estados, proveedores y categorías.

- Los contratos de la API se encuentran en `/contracts/tickets-api.yaml`: revíselos antes de comenzar el desarrollo del backend.
- Consulte `/docs/architecture.md` para obtener una explicación completa del bus de eventos, las banderas de características y cómo evolucionar el proyecto.