package de.vulpescloud.modules.proxy.velocity

import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.modules.proxy.common.ProxyModuleChannels
import de.vulpescloud.modules.proxy.node.commands.ProxyCommand
import de.vulpescloud.modules.proxy.velocity.listener.PlayerJoinListener
import de.vulpescloud.modules.proxy.velocity.manager.MotdManager
import de.vulpescloud.wrapper.Wrapper
import de.vulpescloud.wrapper.redis.RedisJsonParser
import de.vulpescloud.wrapper.redis.RedisManager
import jakarta.inject.Inject
import net.kyori.adventure.text.minimessage.MiniMessage
import org.json.JSONObject
import java.nio.file.Files
import kotlin.io.path.Path

@Plugin(id = "vulpescloud-proxy", name = "VulpesCloud-Proxy", authors = ["TheCGuy"])
@Suppress("unused")
class VelocityEntrypoint @Inject constructor(
    private val eventManager: EventManager,
    private val proxyServer: ProxyServer,
    private val pluginsContainer: PluginContainer
) {
    var configJson = JSONObject(Files.readString(Path("plugins/VulpesCloud-Proxy-Module/config.json")))
        private set

    @Subscribe
    fun onProxyInitializationEvent(event: ProxyInitializeEvent) {
        instance = this
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage()
                .deserialize("<grey>[<aqua>VulpesCloud-Proxy</aqua>]</grey> <yellow>Initializing</yellow>")
        )
        this.eventManager.register(this, MotdManager())
        this.eventManager.register(this, PlayerJoinListener())
        this.listenOnRedisChannels()
    }

    @Subscribe
    fun onProxyShutdownEvent(event: ProxyShutdownEvent) {
        proxyServer.consoleCommandSource.sendMessage(
            MiniMessage.miniMessage().deserialize("<gray>Stopping VulpesCloud-Connector!</gray>")
        )
    }

    private fun listenOnRedisChannels() {
        val manager = Wrapper.instance.getRC()?.let { RedisManager(it.getJedisPool()) }
        manager?.subscribe(listOf(ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)) { _, channel, message ->
            val json = RedisJsonParser.convert(message!!)

            if (json.getString("action") == "PROXY_MODULE" && json.getString("task") == "REFRESH_CONFIG") {
                this.configJson = JSONObject(Files.readString(Path("plugins/VulpesCloud-Proxy-Module/config.json")))
            }
        }
    }

    companion object {
        lateinit var instance: VelocityEntrypoint
    }

}