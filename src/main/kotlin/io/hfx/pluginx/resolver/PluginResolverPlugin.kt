package io.hfx.pluginx.resolver

import io.hfx.pluginx.resolver.injector.InjectorManager
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.plugin.management.PluginResolveDetails

class PluginResolverPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        val injectorManager = InjectorManager(settings)
        settings.pluginManagement.resolutionStrategy.eachPlugin(object : Action<PluginResolveDetails> {
            override fun execute(details: PluginResolveDetails) {
                injectorManager.tryInject(details)
            }
        })
    }
}