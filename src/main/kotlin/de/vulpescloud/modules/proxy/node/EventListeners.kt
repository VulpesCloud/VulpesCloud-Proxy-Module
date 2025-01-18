package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.event.events.service.ServicePrepareEvent
import de.vulpescloud.modules.proxy.node.FileManager.copyConfigIntoService
import de.vulpescloud.modules.proxy.node.FileManager.copyModuleIntoService
import de.vulpescloud.node.Node
import org.slf4j.LoggerFactory

@Suppress("unused")
class EventListeners {
    private val eventManager = Node.instance.eventManager
    private val logger = LoggerFactory.getLogger(EventListeners::class.java)

    val onServicePrepareEvent = eventManager.listen<ServicePrepareEvent> {
        logger.debug("&mProxy-Module &f> &7 Received ServicePrepareEvent")
        logger.info("&mProxy-Module &f> &7 Starting Copy of Proxy Module for &c${it.service.name()}")
        logger.debug("&mProxy-Module &f> &7 Copying Module into Plugins folder of &c${it.service.name()}")
        copyModuleIntoService(it.service)
        logger.debug("&mProxy-Module &f> &7 Copying Config into Plugins folder of &c${it.service.name()}")
        copyConfigIntoService(it.service)
        logger.info("&mProxy-Module &f> &7 Finished Copying of Proxy Module for &c${it.service.name()}")
    }

}