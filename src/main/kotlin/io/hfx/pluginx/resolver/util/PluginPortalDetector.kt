package io.hfx.pluginx.resolver.util

import java.net.URI

object PluginPortalDetector {

    // Gradle 内建插件 ID
    private val builtInPluginIds = setOf(
        "base", "java", "java-library", "application",
        "groovy", "idea", "jacoco", "checkstyle", "pmd", "antlr", "eclipse",
        "maven-publish", "distribution", "signing", "reporting", "help"
    )

    // Kotlin DSL 插件别名，如 kotlin("jvm")
    private val kotlinAliasedPluginIds = setOf(
        "jvm", "multiplatform", "android", "android.library", "plugin.serialization"
    )

    /**
     * 判断是否为 Gradle 官方插件或 Kotlin DSL 插件别名
     */
    fun isOfficialOrBuiltInPlugin(group: String?, pluginId: String): Boolean {
        val isKotlinAliased = group?.startsWith("org.jetbrains.kotlin") == true &&
                pluginId in kotlinAliasedPluginIds
        val isBuiltin = group.isNullOrBlank() || builtInPluginIds.contains(pluginId)
        return isKotlinAliased || isBuiltin
    }

    /**
     * 判断 repo 是否为官方 Plugin Portal 或 Maven Central
     */
    fun isOfficialPluginRepo(repoUrl: String): Boolean {
        return repoUrl.contains("plugins.gradle.org") ||
                repoUrl.contains("repo.maven.apache.org") ||
                repoUrl.contains("mavenCentral()") ||
                URI(repoUrl).scheme == "file"
    }

    /**
     * 综合判断插件是否应跳过处理（官方、Kotlin DSL 别名、无版本号等）
     */
    fun shouldSkipPlugin(group: String?, pluginId: String?, version: String?): Boolean {
        if (pluginId.isNullOrBlank() || version.isNullOrBlank()) return true
        return isOfficialOrBuiltInPlugin(group, pluginId)
    }
}