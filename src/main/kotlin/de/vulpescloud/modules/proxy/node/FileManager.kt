package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.services.Service
import de.vulpescloud.api.services.ServiceFilters
import de.vulpescloud.modules.proxy.common.ProxyModuleChannels
import de.vulpescloud.node.Node
import de.vulpescloud.node.services.LocalServiceImpl
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path

object FileManager {
    private val jarPath = Path("modules/VulpesCloud-Proxy-Module.jar")
    val configPath = Path("modules/VulpesCloud-Proxy-Module/config.json")
    private val logger = LoggerFactory.getLogger(FileManager::class.java)

    fun copyModuleIntoService(service: Service) {
        if (service is LocalServiceImpl) {
            val pluginDir = service.runningDir.resolve("plugins")
            Files.copy(jarPath, pluginDir.resolve("VulpesCloud-Proxy-Module.jar"), StandardCopyOption.REPLACE_EXISTING)

            logger.debug("Copying into {}", pluginDir)
        }
    }

    fun copyConfigIntoService(service: Service) {
        if (service is LocalServiceImpl) {
            val pluginDir = service.runningDir.resolve("plugins").resolve("VulpesCloud-Proxy-Module")
            pluginDir.toFile().mkdirs()
            Files.copy(configPath, pluginDir.resolve("config.json"), StandardCopyOption.REPLACE_EXISTING)
            logger.debug("Copying config into {}", pluginDir)
        }
    }

    fun updateAndPushConfig(config: JSONObject) {
        Files.writeString(configPath, config.toString(4))
        Node.instance.serviceProvider.findServicesByFilter(ServiceFilters.PROXIES)?.forEach {
            copyConfigIntoService(it)
            logger.debug("Updated config in {}", it.name())
        }
        val message = JSONObject()
            .put("action", "PROXY_MODULE")
            .put("task", "REFRESH_CONFIG")

        Node.instance.getRC()?.sendMessage(message.toString(), ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

}