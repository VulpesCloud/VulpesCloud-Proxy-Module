package de.vulpescloud.modules.proxy.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PostLoginEvent
import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.bridge.BridgeAPI
import de.vulpescloud.modules.proxy.velocity.VelocityEntrypoint
import net.kyori.adventure.text.minimessage.MiniMessage

class PlayerJoinListener(private val proxyServer: ProxyServer) {
    @Subscribe
    fun onPlayerJoin(event: PostLoginEvent) {
        val config = VelocityEntrypoint.getConfig()
        if (config.maintenance.active) {
            if (!event.player.hasPermission(config.maintenance.bypassPermission)) {
                event.player.disconnect(
                    MiniMessage.miniMessage().deserialize(config.maintenance.kickMessage)
                )
            }
        } else {
            if (
                proxyServer.playerCount >=
                    BridgeAPI.getFutureAPI()
                        .getServicesAPI()
                        .getLocalService()
                        .get()!!
                        .task
                        .maxPlayers
            ) {
                event.player.disconnect(
                    MiniMessage.miniMessage().deserialize(config.fullKickMessage)
                )
            }
        }
    }
}
