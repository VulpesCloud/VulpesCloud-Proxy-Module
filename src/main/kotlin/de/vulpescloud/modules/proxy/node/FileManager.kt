package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.services.Service
import de.vulpescloud.node.Node
import de.vulpescloud.node.services.LocalServiceImpl
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path

object FileManager {
    private val jarPath = Path("modules/VulpesCloud-Proxy-Module-1.0-SNAPSHOT.jar")
    private val configPath = Path("modules/VulpesCloud-Proxy-Module/config.json")
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

}