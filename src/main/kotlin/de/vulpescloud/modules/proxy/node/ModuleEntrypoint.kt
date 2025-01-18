package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.modules.VulpesModule
import org.slf4j.LoggerFactory

class ModuleEntrypoint : VulpesModule {
    private val logger = LoggerFactory.getLogger(ModuleEntrypoint::class.java)

    override fun disable() {
        logger.info("Bye Bye from ProxyModule")
    }

    override fun enable() {
        EventListeners()
    }
}