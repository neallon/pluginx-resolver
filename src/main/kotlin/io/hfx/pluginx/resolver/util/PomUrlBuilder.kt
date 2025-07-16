package io.hfx.pluginx.resolver.util

import java.net.HttpURLConnection
import java.net.URL

object PomUrlBuilder {

    fun buildGradlePluginPomUrl(
        repoUrl: String,
        group: String,
        pluginId: String,
        pluginVersion: String
    ): String {
        val normalizedRepoUrl = repoUrl.trimEnd('/')

        val combinedGroupId = if (group.isBlank()) pluginId else "$group.$pluginId"
        val groupPath = combinedGroupId.replace('.', '/')
        val artifactId = if (combinedGroupId.endsWith(".gradle.plugin")) {
            combinedGroupId
        } else {
            "$combinedGroupId.gradle.plugin"
        }

        return "$normalizedRepoUrl/$groupPath/$artifactId/$pluginVersion/$artifactId-$pluginVersion.pom"
    }

    fun isPomAvailable(pomUrl: String): Boolean {
        return try {
            val conn = URL(pomUrl).openConnection() as HttpURLConnection
            conn.requestMethod = "HEAD"
            conn.connectTimeout = 3000
            conn.readTimeout = 3000
            conn.connect()
            val result = conn.responseCode in 200..299
            conn.disconnect()
            result
        } catch (e: Exception) {
            false
        }
    }

    fun findAvailablePomUrl(
        repoUrl: String,
        group: String,
        pluginId: String,
        pluginVersion: String
    ): String? {
        val pomUrl = buildGradlePluginPomUrl(repoUrl, group, pluginId, pluginVersion)
        return if (isPomAvailable(pomUrl)) pomUrl else null
    }
}