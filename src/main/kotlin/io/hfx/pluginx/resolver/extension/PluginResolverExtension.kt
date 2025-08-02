package io.hfx.pluginx.resolver.extension

import javax.inject.Inject

open class PluginResolverExtension @Inject constructor() {
    /**
     * 是否自动注入 pluginManagement.repositories，默认 true。
     */
    var autoInjectRepositories: Boolean = true
}