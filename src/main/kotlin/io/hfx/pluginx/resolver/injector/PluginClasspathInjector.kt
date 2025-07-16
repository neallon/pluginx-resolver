package io.hfx.pluginx.resolver.injector

import io.hfx.pluginx.resolver.PluginResolverPlugin
import io.hfx.pluginx.resolver.model.MavenDependency
import io.hfx.pluginx.resolver.model.PluginCoordinates
import io.hfx.pluginx.resolver.util.PluginPortalDetector
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.Logging

object PluginClasspathInjector {
    private val logger = Logging.getLogger(PluginResolverPlugin::class.java)

    private val cache = mutableSetOf<String>()

    fun injectClasspath(settings: Settings, coords: PluginCoordinates, dependencies: List<MavenDependency>) {
        val notation = "${coords.group}:${coords.id}:${coords.version}"
        if (PluginPortalDetector.isOfficialOrBuiltInPlugin(coords.group, coords.id)) {
            logger.lifecycle("Skip injecting official/built-in plugin: $notation")
            return
        }
        if (cache.contains(notation)) {
            logger.lifecycle("Classpath already injected: $notation")
            return
        }

        settings.buildscript.dependencies.add("classpath", notation)
        logger.lifecycle("Injected plugin classpath: $notation")

        for (dep in dependencies) {
            if (dep.packaging != "pom") {
                val depNotation = "${dep.groupId}:${dep.artifactId}:${dep.version}"
                settings.buildscript.dependencies.add("classpath", depNotation)
                logger.lifecycle("Injected plugin dependency classpath: $depNotation")
            }
        }

        cache.add(notation)
    }
}