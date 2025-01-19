package de.vulpescloud.modules.proxy.velocity.manager

import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.plugin.PluginContainer
import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.wrapper.Wrapper
import net.kyori.adventure.text.minimessage.MiniMessage
import org.json.JSONObject
import kotlin.jvm.optionals.getOrNull

class MotdManager(
    val eventManager: EventManager,
    val proxyServer: ProxyServer,
    val pluginsContainer: PluginContainer,
    val configJSON: JSONObject
) {

    @Subscribe
    fun onProxyPingEvent(event: ProxyPingEvent) {
        val motdJson = configJSON.getJSONObject("motd")

        val fistLine = motdJson.getString("firstLine")
            .replace("%proxy%", Wrapper.instance.service.name)
            .replace("%playerCount%", "${event.ping.players.getOrNull()?.online ?: 0}")
            .replace("%playerMax%", "${event.ping.players.getOrNull()?.max ?: 0}")

        val secondLine = motdJson.getString("secondLine")
            .replace("%proxy%", Wrapper.instance.service.name)
            .replace("%playerCount%", "${event.ping.players.getOrNull()?.online ?: 0}")
            .replace("%playerMax%", "${event.ping.players.getOrNull()?.max ?: 0}")

        val samplePlayers = event.ping.players.getOrNull()?.sample ?: emptyList()
        val motd = MiniMessage.miniMessage().deserialize("$fistLine\n$secondLine")
        val builder = event.ping.asBuilder()
            .version(event.ping.version)
            .onlinePlayers(event.ping.players.getOrNull()?.online ?: 0)
            .maximumPlayers(event.ping.players.getOrNull()?.max ?: 0)
            .samplePlayers(*samplePlayers.toTypedArray())
            .description(motd)

        event.ping = builder.build()
    }

}