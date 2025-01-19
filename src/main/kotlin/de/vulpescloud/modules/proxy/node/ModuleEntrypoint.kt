package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.modules.VulpesModule
import org.slf4j.LoggerFactory
import java.nio.file.Files
import kotlin.io.path.Path

class ModuleEntrypoint : VulpesModule {
    private val logger = LoggerFactory.getLogger(ModuleEntrypoint::class.java)

    override fun disable() {
        logger.info("Bye Bye from ProxyModule")
    }

    override fun enable() {
        if (!Path("modules/VulpesCloud-Proxy-Module/config.json").toFile().exists()) {
            this::class.java.getResourceAsStream("config.json")?.let {
                Files.copy(
                    it,
                    Path("modules/VulpesCloud-Proxy-Module/config.json")
                )
            }
        }

        EventListeners()
    }
}