package io.hfx.pluginx.resolver.strategy

import io.hfx.pluginx.resolver.model.MavenDependency
import io.hfx.pluginx.resolver.model.PluginCoordinates
import org.gradle.api.logging.Logging

class CacheResolutionStrategy : ResolutionStrategy {
    private val logger = Logging.getLogger(CacheResolutionStrategy::class.java)

    private val cache = mutableMapOf<PluginCoordinates, List<MavenDependency>>()

    override fun resolve(coords: PluginCoordinates): List<MavenDependency>? {
        if (cache.containsKey(coords)) {
            logger.info("Cache hit for $coords")
            return cache[coords]
        }
        logger.info("Cache miss for $coords")
        return null
    }

    fun put(coords: PluginCoordinates, deps: List<MavenDependency>) {
        cache[coords] = deps
    }
}