# PluginX Resolver — Gradle Plugin Classpath Auto Resolver

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?label=Gradle%20Plugin&metadataUrl=https://plugins.gradle.org/m2/io/github/neallon/pluginx/resolver/io.github.neallon.pluginx.resolver.gradle.plugin/maven-metadata.xml)](https://plugins.gradle.org/plugin/io.github.neallon.pluginx.resolver)

**PluginX Resolver** automatically resolves and injects Gradle plugin classpath dependencies based on the `pluginManagement.resolutionStrategy`.

---

## 🔧 Plugin ID

```kotlin
plugins {
    id("io.github.neallon.pluginx.resolver") version "1.1.0"
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

## ❌ Disable Automatic Repository Injection

By default, PluginX Resolver automatically injects repositories parsed from Gradle properties files (`~/.gradle/gradle.properties` and project `gradle.properties`) with keys like:

```
hfx.<repoId>.repo.url=
hfx.<repoId>.repo.user=
hfx.<repoId>.repo.password=
```

To disable this behavior, set in your `settings.gradle.kts`:

```kotlin
pluginxResolver {
    autoInjectRepositories = false
}
```

---

## 📋 Repository Properties Format

The plugin reads repositories from Gradle properties files with the following naming conventions:

- `hfx.<repoId>.repo.url` - Repository URL
- `hfx.<repoId>.repo.user` - Username for authentication (optional)
- `hfx.<repoId>.repo.password` - Password for authentication (optional)
- `hfx.<repoId>.repo.allowInsecure` - Whether to allow insecure HTTP protocol (optional, defaults true if URL starts with http)

Example:

```
hfx.myrepo.repo.url=https://my.repo.url/maven
hfx.myrepo.repo.user=yourUsername
hfx.myrepo.repo.password=yourPassword
hfx.myrepo.repo.allowInsecure=false
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