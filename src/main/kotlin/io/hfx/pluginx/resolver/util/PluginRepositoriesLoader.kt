package io.hfx.pluginx.resolver.util

import io.github.neallon.repo.GradlePropertiesRepositoryLoader
import io.github.neallon.repo.model.ResolvedRepository
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.Logging

object PluginRepositoriesLoader {
    private val logger = Logging.getLogger(PluginRepositoriesLoader::class.java)

    fun load(settings: Settings): List<ResolvedRepository> {
        return try {
            val loader = GradlePropertiesRepositoryLoader(
                gradleUserHomeDir = settings.gradle.gradleUserHomeDir,
                projectRootDir = settings.rootDir
            )
            val repos = loader.loadRepositories()

            if (repos.isEmpty()) {
                logger.debug("[pluginx-resolver] No repositories found from gradle.properties")
            } else {
                logger.debug("[pluginx-resolver] Loaded ${repos.size} repository(ies) from gradle.properties")
            }

            repos
        } catch (e: Exception) {
            logger.error("[pluginx-resolver] Failed to load repositories: ${e.message}", e)
            emptyList()
        }
    }
}