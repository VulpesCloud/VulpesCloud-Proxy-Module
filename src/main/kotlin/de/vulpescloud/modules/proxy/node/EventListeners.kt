package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.event.EventListener
import de.vulpescloud.api.event.events.service.ServiceStateChangeEvent
import de.vulpescloud.api.service.ServiceStates
import de.vulpescloud.node.VulpesNode
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path

@Suppress("unused")
class EventListeners {
    private val logger = LoggerFactory.getLogger(EventListeners::class.java)
    private val clusterProvider = VulpesNode.clusterProvider
    private val jarPath = Path("modules/VulpesCloud-Proxy-Module.jar")

    @EventListener
    fun onServiceStateChangeEvent(event: ServiceStateChangeEvent) {
        if (
            event.serviceInfo.runningNode.name == clusterProvider.localNode().name &&
                event.newState == ServiceStates.PREPARED
        ) {
            // val pluginDir = event.serviceInfo.path().resolve("plugins")
//            Files.copy(
//                jarPath,
//                pluginDir.resolve("VulpesCloud-Proxy-Module.jar"),
//                StandardCopyOption.REPLACE_EXISTING,
//            )
        }
    }
}
