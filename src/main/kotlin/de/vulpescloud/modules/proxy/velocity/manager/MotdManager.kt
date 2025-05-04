package de.vulpescloud.modules.proxy.velocity.manager

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import de.vulpescloud.bridge.VulpesBridge
import de.vulpescloud.modules.proxy.velocity.VelocityEntrypoint
import net.kyori.adventure.text.minimessage.MiniMessage
import kotlin.jvm.optionals.getOrNull

class MotdManager {

    private val serviceName = VulpesBridge.getServiceProvider().getLocalService().name

    @Subscribe
    fun onProxyPingEvent(event: ProxyPingEvent) {
        val config = VelocityEntrypoint.instance.config

        if (
            config.getEntry("motd.enabled", true) && !config.getEntry("maintenance.active", false)
        ) {
            val fistLine =
                config
                    .getEntry("motd.firstLine", "")
                    .replace("%proxy%", serviceName)
                    .replace("%playerCount%", "${event.ping.players.getOrNull()?.online ?: 0}")
                    .replace("%playerMax%", "${event.ping.players.getOrNull()?.max ?: 0}")

            val secondLine =
                config
                    .getEntry("motd.secondLine", "")
                    .replace("%proxy%", serviceName)
                    .replace("%playerCount%", "${event.ping.players.getOrNull()?.online ?: 0}")
                    .replace("%playerMax%", "${event.ping.players.getOrNull()?.max ?: 0}")

            val samplePlayers = event.ping.players.getOrNull()?.sample ?: emptyList()
            val motd = MiniMessage.miniMessage().deserialize("$fistLine\n$secondLine")
            val builder =
                event.ping
                    .asBuilder()
                    .version(event.ping.version)
                    .onlinePlayers(event.ping.players.getOrNull()?.online ?: 0)
                    .maximumPlayers(event.ping.players.getOrNull()?.max ?: 0)
                    .samplePlayers(*samplePlayers.toTypedArray())
                    .description(motd)

            event.ping = builder.build()
        } else if (config.getEntry("maintenance.active", false)) {
            val fistLine =
                config
                    .getEntry("maintenance.firstLine", "")
                    .replace("%proxy%", serviceName)
                    .replace("%playerCount%", "${event.ping.players.getOrNull()?.online ?: 0}")
                    .replace("%playerMax%", "${event.ping.players.getOrNull()?.max ?: 0}")

            val secondLine =
                config
                    .getEntry("maintenance.secondLine", "")
                    .replace("%proxy%", serviceName)
                    .replace("%playerCount%", "${event.ping.players.getOrNull()?.online ?: 0}")
                    .replace("%playerMax%", "${event.ping.players.getOrNull()?.max ?: 0}")

            val samplePlayers = event.ping.players.getOrNull()?.sample ?: emptyList()
            val motd = MiniMessage.miniMessage().deserialize("$fistLine\n$secondLine")
            val builder =
                event.ping
                    .asBuilder()
                    .version(event.ping.version)
                    .onlinePlayers(event.ping.players.getOrNull()?.online ?: 0)
                    .maximumPlayers(event.ping.players.getOrNull()?.max ?: 0)
                    .samplePlayers(*samplePlayers.toTypedArray())
                    .description(motd)

            event.ping = builder.build()
        }
    }
}
