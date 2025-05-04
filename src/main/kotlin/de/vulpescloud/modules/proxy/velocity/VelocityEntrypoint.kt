package de.vulpescloud.modules.proxy.velocity

import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.api.virtualconfig.VirtualConfig
import de.vulpescloud.modules.proxy.velocity.listener.PlayerJoinListener
import de.vulpescloud.modules.proxy.velocity.manager.MotdManager
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.MiniMessage

@Plugin(id = "vulpescloud-proxy-module", name = "VulpesCloud-Proxy-Module", authors = ["TheCGuy"])
@Suppress("unused")
class VelocityEntrypoint
@Inject
constructor(
    private val eventManager: EventManager,
    private val proxyServer: ProxyServer,
    private val pluginsContainer: PluginContainer,
) {
    lateinit var config: VirtualConfig
    private lateinit var redisChannelListener: RedisChannelListener

    @Subscribe
    fun onProxyInitializationEvent(event: ProxyInitializeEvent) {
        instance = this
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage()
                .deserialize(
                    "<grey>[<aqua>VulpesCloud-Proxy-Module</aqua>]</grey> <yellow>Initializing</yellow>"
                )
        )

        this.eventManager.register(this, MotdManager())
        this.eventManager.register(this, PlayerJoinListener())
        this.redisChannelListener = RedisChannelListener(config, proxyServer)
    }

    @Subscribe
    fun onProxyShutdownEvent(event: ProxyShutdownEvent) {
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage().deserialize("<gray>Stopping VulpesCloud-Connector!</gray>")
        )
    }

    companion object {
        lateinit var instance: VelocityEntrypoint
    }
}
