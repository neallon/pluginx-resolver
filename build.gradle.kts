plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.3.1"
}

group = "io.github.neallon"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
}

gradlePlugin {
    plugins {
        create("pluginResolver") {
            id = "io.github.neallon.pluginx.resolver"
            implementationClass = "io.hfx.pluginx.resolver.PluginResolverPlugin"
            displayName = "PluginX Resolver — Gradle Plugin Classpath Auto Resolver"
            description = "Automatically resolves and injects plugin classpath dependencies based on pluginManagement resolutionStrategy."
            tags = listOf("classpath-injection", "plugin-resolution", "plugin-classpath", "dependency-management", "pluginManagement")
            website = "https://github.com/neallon/pluginx-resolver"
            vcsUrl = "https://github.com/neallon/pluginx-resolver.git"
        }
    }
}
