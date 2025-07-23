# PluginX Resolver — Gradle Plugin Classpath Auto Resolver

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?label=Gradle%20Plugin&metadataUrl=https://plugins.gradle.org/m2/io/github/neallon/pluginx/resolver/io.github.neallon.pluginx.resolver.gradle.plugin/maven-metadata.xml)](https://plugins.gradle.org/plugin/io.github.neallon.pluginx.resolver)

**PluginX Resolver** automatically resolves and injects Gradle plugin classpath dependencies based on the `pluginManagement.resolutionStrategy`.

---

## 🔧 Plugin ID

```kotlin
plugins {
    id("io.github.neallon.pluginx.resolver") version "1.0.1"
}
```

---

## ✨ Features

- Automatically detects plugin ID and version in `pluginManagement`.
- Resolves plugin POM from any custom Maven repository.
- Extracts and injects plugin `classpath` dependencies into `buildscript`.
- Reduces manual `classpath` maintenance for custom plugins.

---

## 🧪 Usage Example

```kotlin
pluginManagement {
    repositories {
        maven { url = uri("https://your.custom.repo") }
    }
    resolutionStrategy {
        eachPlugin {
            // PluginX Resolver will handle classpath resolution
        }
    }
}
```

---

## 📎 Metadata

- **Plugin ID**: `io.github.neallon.pluginx.resolver`
- **Implementation Class**: `io.hfx.pluginx.resolver.PluginResolverPlugin`
- **Website**: [https://github.com/neallon/pluginx-resolver](https://github.com/neallon/pluginx-resolver)
- **VCS**: [https://github.com/neallon/pluginx-resolver.git](https://github.com/neallon/pluginx-resolver.git)
- **Tags**: gradle, plugin, classpath, resolver, dependency, auto, injection, pluginManagement

---

## 📄 License

Licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0).
