# PluginX Resolver — Gradle 插件类路径自动解析器

[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?label=Gradle%20Plugin&metadataUrl=https://plugins.gradle.org/m2/io/github/neallon/pluginx/resolver/io.github.neallon.pluginx.resolver.gradle.plugin/maven-metadata.xml)](https://plugins.gradle.org/plugin/io.github.neallon.pluginx.resolver)

**PluginX Resolver** 是一个 Gradle 插件，用于基于 `pluginManagement.resolutionStrategy` 自动解析并注入插件类路径依赖。

---

## 🔧 插件 ID

```kotlin
plugins {
    id("io.github.neallon.pluginx.resolver") version "1.0.0"
}
```

---

## ✨ 功能亮点

- 自动识别 `pluginManagement` 中的插件 ID 与版本。
- 支持从任意 Maven 仓库解析插件 POM。
- 自动提取依赖并注入 `buildscript.classpath`。
- 避免手动维护插件依赖，简化构建配置。

---

## 🧪 使用示例

```kotlin
pluginManagement {
    repositories {
        maven { url = uri("https://your.custom.repo") }
    }
    resolutionStrategy {
        eachPlugin {
            // PluginX Resolver 将自动处理插件依赖
        }
    }
}
```

---

## 📎 插件元信息

- **插件 ID**：`io.github.neallon.pluginx.resolver`
- **实现类**：`io.hfx.pluginx.resolver.PluginResolverPlugin`
- **项目主页**：[https://github.com/neallon/pluginx-resolver](https://github.com/neallon/pluginx-resolver)
- **源码仓库**：[https://github.com/neallon/pluginx-resolver.git](https://github.com/neallon/pluginx-resolver.git)
- **关键词**：gradle, plugin, classpath, resolver, dependency, auto, injection, pluginManagement

---

## 📄 开源协议

使用 [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0) 协议发布。
