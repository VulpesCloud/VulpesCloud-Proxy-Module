package de.vulpescloud.modules.proxy.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PostLoginEvent
import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.modules.proxy.velocity.VelocityEntrypoint
import kotlinx.coroutines.runBlocking
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
                    runBlocking {
                        VelocityEntrypoint.bridgeAPI
                            .getServicesAPI()
                            .getLocalService()!!
                            .task
                            .maxPlayers
                    }
            ) {
                event.player.disconnect(
                    MiniMessage.miniMessage().deserialize(config.fullKickMessage)
                )
            }
        }
    }
}
