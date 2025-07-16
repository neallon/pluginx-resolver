package io.hfx.pluginx.resolver.strategy

import io.hfx.pluginx.resolver.model.MavenDependency
import io.hfx.pluginx.resolver.model.PluginCoordinates
import org.gradle.api.logging.Logging

class MetadataBasedResolution : ResolutionStrategy {
    private val logger = Logging.getLogger(MetadataBasedResolution::class.java)

    override fun resolve(coords: PluginCoordinates): List<MavenDependency>? {
        logger.info("Trying metadata based resolution for $coords")
        // 简单模拟返回空，待扩展
        return null
    }
}