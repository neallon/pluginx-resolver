package io.hfx.pluginx.resolver.model

import org.gradle.plugin.management.PluginResolveDetails

data class PluginCoordinates(
    val group: String,
    val id: String,
    val version: String
) {
    companion object {
        fun from(details: PluginResolveDetails): PluginCoordinates {
            val requested = details.requested
            val fullId = requested.id.id
            val version = requested.version ?: ""

            val lastDotIndex = fullId.lastIndexOf('.')
            return if (lastDotIndex >= 0) {
                val group = fullId.substring(0, lastDotIndex)
                val id = fullId.substring(lastDotIndex + 1)
                PluginCoordinates(group, id, version)
            } else {
                PluginCoordinates("", fullId, version)
            }
        }
    }
}