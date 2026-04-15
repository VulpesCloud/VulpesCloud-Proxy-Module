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
import de.vulpescloud.modules.proxy.velocity.commands.VulpesCloudProxyCommand
import de.vulpescloud.modules.proxy.velocity.listener.PlayerJoinListener
import de.vulpescloud.modules.proxy.velocity.manager.MotdManager
import jakarta.inject.Inject
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking
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

        val commandManager = proxyServer.commandManager
        val vulpescloudProxyCommand = VulpesCloudProxyCommand().command
        val meta = commandManager.metaBuilder(vulpescloudProxyCommand).plugin(this).build()

        commandManager.register(meta, vulpescloudProxyCommand)
    }

    @Subscribe
    fun onProxyShutdownEvent(event: ProxyShutdownEvent) {
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage().deserialize("<gray>Stopping VulpesCloud-Connector!</gray>")
        )
    }

    companion object {
        lateinit var instance: VelocityEntrypoint
        val bridgeAPI = BridgeAPI.createCoroutineAPI()

        fun getConfig(): ProxyModuleConfig {
            return CompletableFuture.supplyAsync {
                    runBlocking {
                        bridgeAPI
                            .getVirtualConfigAPI()
                            .getCustomConfigObject("module_proxy", ProxyModuleConfig.serializer())
                            ?: throw Exception("Config is null!")
                    }
                }
                .get(5, TimeUnit.SECONDS)
        }
    }
}
