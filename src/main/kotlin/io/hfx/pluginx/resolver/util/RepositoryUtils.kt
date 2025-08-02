package io.hfx.pluginx.resolver.util

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import java.net.URI

object RepositoryUtils {

    /**
     * Checks if a repository with the given URL already exists in the RepositoryHandler.
     * Compatible with pluginManagement.repositories.
     */
    fun hasRepositoryWithUrl(handler: RepositoryHandler, targetUrl: String): Boolean {
        return handler.any { repo ->
            extractRepositoryUrl(repo)?.toString() == targetUrl
        }
    }

    /**
     * Extracts the URL from a repository object.
     * Uses reflection to ensure compatibility with pluginManagement phase.
     */
    fun extractRepositoryUrl(repo: Any): URI? {
        return try {
            when (repo) {
                is MavenArtifactRepository -> repo.url
                else -> {
                    // Use reflection to call getUrl() method if available
                    val method = repo.javaClass.methods.firstOrNull { it.name == "getUrl" && it.parameterCount == 0 }
                    method?.invoke(repo) as? URI
                }
            }
        } catch (_: Exception) {
            null
        }
    }
}