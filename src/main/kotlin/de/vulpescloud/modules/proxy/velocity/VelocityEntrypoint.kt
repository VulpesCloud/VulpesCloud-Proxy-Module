package de.vulpescloud.modules.proxy.velocity

import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.proxy.ProxyServer
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.MiniMessage

@Plugin(id = "vulpescloud-proxy", name = "VulpesCloud-Proxy", authors = ["TheCGuy"])
@Suppress("unused")
class VelocityEntrypoint @Inject constructor(
    val eventManager: EventManager,
    val proxyServer: ProxyServer,
    val pluginsContainer: PluginContainer
) {

    @Subscribe
    fun onProxyInitializationEvent(event: ProxyInitializeEvent) {
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage()
                .deserialize("<grey>[<aqua>VulpesCloud-Proxy</aqua>]</grey> <yellow>Initializing</yellow>")
        )

    }

    @Subscribe
    fun onProxyShutdownEvent(event: ProxyShutdownEvent) {
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage().deserialize("<gray>Stopping VulpesCloud-Connector!</gray>")
        )
    }

}