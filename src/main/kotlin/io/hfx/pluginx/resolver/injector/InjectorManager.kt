package io.hfx.pluginx.resolver.injector

import io.hfx.pluginx.resolver.model.PluginCoordinates
import io.hfx.pluginx.resolver.strategy.CacheResolutionStrategy
import io.hfx.pluginx.resolver.strategy.InjectedPomResolution
import io.hfx.pluginx.resolver.strategy.MetadataBasedResolution
import io.hfx.pluginx.resolver.strategy.PomBasedResolution
import io.hfx.pluginx.resolver.util.PluginPortalDetector
import io.hfx.pluginx.resolver.util.PluginRepositoriesLoader
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.Logging
import org.gradle.plugin.management.PluginResolveDetails
import java.net.URI

class InjectorManager(private val settings: Settings) {

    private val logger = Logging.getLogger(InjectorManager::class.java)

    private val resolvedRepositories = PluginRepositoriesLoader.load(settings)

    private val resolvedRepos: List<URI> = resolvedRepositories.map { URI(it.url) }

    private val customRepos: List<URI> = settings.pluginManagement.repositories
        .filterIsInstance<MavenArtifactRepository>()
        .map { it.url }
        .filterNot { repoUrl ->
            PluginPortalDetector.isOfficialPluginRepo(repoUrl.toString()) || resolvedRepos.any { it == repoUrl }
        }

    private val strategies = listOf(
        CacheResolutionStrategy(),
        InjectedPomResolution(resolvedRepos),
        PomBasedResolution(customRepos),
        MetadataBasedResolution()
    )

    fun tryInject(details: PluginResolveDetails) {
        val coords = PluginCoordinates.from(details)

        if (coords.version.isBlank()) {
            logger.info("Skip plugin with empty version: $coords")
            return
        }

        if (PluginPortalDetector.isOfficialOrBuiltInPlugin(coords.group, coords.id)) {
            logger.info("Skip official or built-in plugin: $coords")
            return
        }

        for (strategy in strategies) {
            val deps = strategy.resolve(coords)
            if (!deps.isNullOrEmpty()) {
                PluginClasspathInjector.injectClasspath(settings, coords, deps)
                return
            }
        }

        // Debug only, no warning
        logger.debug("No classpath injection needed for plugin {}", coords)
    }

    fun injectRepositories() {
        settings.pluginManagement.repositories.apply {
            resolvedRepositories.forEach { repo ->
                maven {
                    name = repo.name
                    url = URI(repo.url)
                    credentials {
                        username = repo.user
                        password = repo.password
                    }
                    isAllowInsecureProtocol = repo.allowInsecureProtocol
                }

                logger.info("[pluginx-resolver] Injected repository: ${repo.name} ${repo.url}")
            }
        }

    }
}