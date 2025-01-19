package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.modules.VulpesModule
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.util.Objects
import kotlin.io.path.Path

class ModuleEntrypoint : VulpesModule {
    private val logger = LoggerFactory.getLogger(ModuleEntrypoint::class.java)

    override fun disable() {
        logger.info("Bye Bye from ProxyModule")
    }

    override fun enable() {
        if (!Path("modules/VulpesCloud-Proxy-Module/config.json").toFile().exists()) {
            logger.debug("Copying defualt config")
            Path("modules/VulpesCloud-Proxy-Module").toFile().mkdirs()
            Files.copy(
                Objects.requireNonNull(this::class.java.classLoader.getResourceAsStream("config.json")),
                Path("modules/VulpesCloud-Proxy-Module/config.json")
            )
        }

        EventListeners()
    }
}