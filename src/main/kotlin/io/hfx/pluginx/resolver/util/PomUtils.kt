package io.hfx.pluginx.resolver.util

import io.hfx.pluginx.resolver.model.MavenDependency
import org.gradle.api.logging.Logging

object PomUtils {
    private val logger = Logging.getLogger(PomUtils::class.java)

    fun resolveDependencies(
        repoUrl: String,
        group: String,
        pluginId: String,
        pluginVersion: String
    ): List<MavenDependency> {
        val pluginPomUrl = PomUrlBuilder.findAvailablePomUrl(repoUrl, group, pluginId, pluginVersion)
            ?: return logger.run {
                error("❌ Unable to resolve POM for plugin: $group:$pluginId:$pluginVersion from $repoUrl")
                emptyList()
            }

        logger.info("fetch POM from $pluginPomUrl")
        val pluginPomDoc = fetchXml(pluginPomUrl) ?: return emptyList()

        val implDependencies = mutableListOf<MavenDependency>()
        val depsNode = pluginPomDoc.getElementsByTagName("dependencies").item(0)

        if (depsNode != null) {
            val deps = depsNode.childNodes
            for (i in 0 until deps.length) {
                val depNode = deps.item(i)
                if (depNode.nodeName == "dependency") {
                    parseDependencyNode(depNode)?.let { implDependencies += it }
                }
            }
        }

        return implDependencies
    }

    private fun fetchXml(url: String): org.w3c.dom.Document? {
        return try {
            val factory = javax.xml.parsers.DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            builder.parse(java.net.URL(url).openStream())
        } catch (e: Exception) {
            null
        }
    }

    private fun parseDependencyNode(node: org.w3c.dom.Node): MavenDependency? {
        val children = node.childNodes
        var groupId: String? = null
        var artifactId: String? = null
        var version: String? = null
        var packaging = "jar"

        for (i in 0 until children.length) {
            val child = children.item(i)
            when (child.nodeName) {
                "groupId" -> groupId = child.textContent?.trim()
                "artifactId" -> artifactId = child.textContent?.trim()
                "version" -> version = child.textContent?.trim()
                "type" -> packaging = child.textContent?.trim() ?: "jar"
            }
        }

        return if (groupId != null && artifactId != null && version != null) {
            MavenDependency(groupId, artifactId, version, packaging)
        } else null
    }
}