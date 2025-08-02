# PluginX Resolver — Gradle 插件类路径自动解析器

[![Gradle 插件门户](https://img.shields.io/maven-metadata/v?label=Gradle%20Plugin&metadataUrl=https://plugins.gradle.org/m2/io/github/neallon/pluginx/resolver/io.github.neallon.pluginx.resolver.gradle.plugin/maven-metadata.xml)](https://plugins.gradle.org/plugin/io.github.neallon.pluginx.resolver)

**PluginX Resolver** 会基于 `pluginManagement.resolutionStrategy` 自动解析并注入 Gradle 插件的类路径依赖。

---

## 🔧 插件ID

```kotlin
plugins {
    id("io.github.neallon.pluginx.resolver") version "1.1.0"
}
```

---

## ✨ 功能特性

- 自动检测 `pluginManagement` 中插件的 ID 和版本。
- 支持从任意自定义 Maven 仓库解析插件 POM。
- 自动提取并注入插件的 `classpath` 依赖到 `buildscript`。
- 减少自定义插件手动维护 classpath 的麻烦。

---

## 🧪 使用示例

```kotlin
pluginManagement {
    repositories {
        maven { url = uri("https://your.custom.repo") }
    }
    resolutionStrategy {
        eachPlugin {
            // PluginX Resolver 将自动处理 classpath 解析
        }
    }
}
```

---

## ❌ 禁用自动注入仓库

默认情况下，PluginX Resolver 会自动从 Gradle 属性文件（`~/.gradle/gradle.properties` 和项目根目录的 `gradle.properties`）读取以下格式的仓库配置并注入：

```
hfx.<repoId>.repo.url=
hfx.<repoId>.repo.user=
hfx.<repoId>.repo.password=
```

如果想关闭自动注入功能，在 `settings.gradle.kts` 中设置：

```kotlin
pluginxResolver {
    autoInjectRepositories = false
}
```

---

## 📋 仓库属性格式

插件从 Gradle 属性文件中读取仓库配置，格式如下：

- `hfx.<repoId>.repo.url` - 仓库 URL
- `hfx.<repoId>.repo.user` - 认证用户名（可选）
- `hfx.<repoId>.repo.password` - 认证密码（可选）
- `hfx.<repoId>.repo.allowInsecure` - 是否允许使用非安全 HTTP 协议（可选，默认如果 URL 以 http 开头则为 true）

示例：

```
hfx.myrepo.repo.url=https://my.repo.url/maven
hfx.myrepo.repo.user=yourUsername
hfx.myrepo.repo.password=yourPassword
hfx.myrepo.repo.allowInsecure=false
```

---

## 📎 插件元信息

- **插件 ID**: `io.github.neallon.pluginx.resolver`
- **实现类**: `io.hfx.pluginx.resolver.PluginResolverPlugin`
- **官网**: [https://github.com/neallon/pluginx-resolver](https://github.com/neallon/pluginx-resolver)
- **源码仓库**: [https://github.com/neallon/pluginx-resolver.git](https://github.com/neallon/pluginx-resolver.git)
- **标签**: gradle, plugin, classpath, resolver, dependency, auto, injection, pluginManagement

---

## 📄 许可证

基于 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0) 许可。