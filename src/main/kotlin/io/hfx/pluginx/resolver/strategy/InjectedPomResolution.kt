package io.hfx.pluginx.resolver.strategy

import io.hfx.pluginx.resolver.model.MavenDependency
import io.hfx.pluginx.resolver.model.PluginCoordinates
import io.hfx.pluginx.resolver.util.PomUtils
import org.gradle.api.logging.Logging
import java.net.URI

class InjectedPomResolution(
    private val repos: List<URI>
) : ResolutionStrategy {
    private val logger = Logging.getLogger(InjectedPomResolution::class.java)

    override fun resolve(coords: PluginCoordinates): List<MavenDependency>? {
        logger.info("Trying POM-injected resolution for {}", coords)

        for (repo in repos) {
            try {
                val deps = PomUtils.resolveDependencies(repo.toString(), coords.group, coords.id, coords.version)
                if (deps.isNotEmpty()) {
                    logger.info("PomInjected Resolved {} dependencies for {} from {}", deps.size, coords, repo)
                    return deps
                }
            } catch (e: Exception) {
                logger.warn("Failed to load POM from $repo for $coords: ${e.message}")
            }
        }

        return null
    }
}