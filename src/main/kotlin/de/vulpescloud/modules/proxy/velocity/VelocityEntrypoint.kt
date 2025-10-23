package de.vulpescloud.modules.proxy.velocity

import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.bridge.BridgeAPI
import de.vulpescloud.modules.proxy.common.config.ProxyModuleConfig
import de.vulpescloud.modules.proxy.common.event.ProxyModuleConfigUpdateEvent
import de.vulpescloud.modules.proxy.velocity.listener.PlayerJoinListener
import de.vulpescloud.modules.proxy.velocity.manager.MotdManager
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@Plugin(id = "vulpescloud-proxy-module", name = "VulpesCloud-Proxy-Module", authors = ["TheCGuy"])
@Suppress("unused")
class VelocityEntrypoint
@Inject
constructor(
    private val eventManager: EventManager,
    private val proxyServer: ProxyServer,
    private val pluginsContainer: PluginContainer,
) {

    private var job: Job? = null

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
        this.eventManager.register(this, PlayerJoinListener(proxyServer))

        job =
            BridgeAPI.getCoroutineAPI().getEventAPI().subscribe<ProxyModuleConfigUpdateEvent> {
                proxyServer.consoleCommandSource.sendMessage(
                    MiniMessage.miniMessage()
                        .deserialize(
                            "<grey>[<aqua>VulpesCloud-Proxy-Module</aqua>]</grey> Pulling new configuration!"
                        )
                )
                BridgeAPI.getCoroutineAPI()
                    .getVirtualConfigAPI()
                    .updateLocalConfigFromDatabase("module_proxy")
                proxyServer.consoleCommandSource.sendMessage(
                    MiniMessage.miniMessage()
                        .deserialize(
                            "<grey>[<aqua>VulpesCloud-Proxy-Module</aqua>]</grey> <gray>Successfully pulled new configuration!</gray>"
                        )
                )
            }
    }

    @Subscribe
    fun onProxyShutdownEvent(event: ProxyShutdownEvent) {
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage().deserialize("<gray>Stopping VulpesCloud-Connector!</gray>")
        )
        job?.cancel()
        job = null
    }

    companion object {
        lateinit var instance: VelocityEntrypoint

        fun getConfig(): ProxyModuleConfig {
            return CompletableFuture.supplyAsync {
                    runBlocking {
                        BridgeAPI.getCoroutineAPI()
                            .getVirtualConfigAPI()
                            .getCustomConfigObject("module_proxy", ProxyModuleConfig.serializer())
                            ?: throw Exception("Config is null!")
                    }
                }
                .get(5, TimeUnit.SECONDS)
        }
    }
}
