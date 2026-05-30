package com.moviles.paninisupport.features

/**
 * Central feature flag registry for the Panini Support PoC.
 *
 * Flags are defined as simple booleans here so the team can enable or disable
 * features without modifying multiple files. In a production system these values
 * would be fetched from a remote configuration service (e.g. Firebase Remote Config).
 */
object FeatureFlags {

    /**
     * Controls whether agents can open the "Create Ticket" form.
     * Disable during read-only support windows or while the creation API is unavailable.
     */
    const val CREATE_TICKET_ENABLED = true

    /**
     * Controls whether agents can change a ticket's priority from the detail screen.
     * Disable to restrict priority changes to team leads only (enforced server-side in production).
     */
    const val PRIORITY_UPDATE_ENABLED = true
}
