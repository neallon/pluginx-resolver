package io.hfx.pluginx.resolver.strategy

import io.hfx.pluginx.resolver.model.MavenDependency
import io.hfx.pluginx.resolver.model.PluginCoordinates

interface ResolutionStrategy {
    fun resolve(coords: PluginCoordinates): List<MavenDependency>?
}