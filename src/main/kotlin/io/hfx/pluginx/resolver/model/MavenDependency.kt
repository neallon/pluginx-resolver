package io.hfx.pluginx.resolver.model

data class MavenDependency(
    val groupId: String,
    val artifactId: String,
    val version: String,
    val packaging: String = "jar"
)