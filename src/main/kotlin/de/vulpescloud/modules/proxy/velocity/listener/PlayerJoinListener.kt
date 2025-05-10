package de.vulpescloud.modules.proxy.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PostLoginEvent
import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.bridge.VulpesBridge
import de.vulpescloud.modules.proxy.velocity.VelocityEntrypoint
import net.kyori.adventure.text.minimessage.MiniMessage

class PlayerJoinListener(private val proxyServer: ProxyServer) {
    @Subscribe
    fun onPlayerJoin(event: PostLoginEvent) {
        val config = VelocityEntrypoint.instance.config
        if (config.getEntry("maintenance.active", false)) {
            if (!event.player.hasPermission(config.getEntry("maintenance.bypassPermission", ""))) {
                event.player.disconnect(
                    MiniMessage.miniMessage()
                        .deserialize(config.getEntry("maintenance.kickMessage", ""))
                )
            }
        } else {
            if (
                proxyServer.playerCount >=
                    VulpesBridge.getServiceProvider().getLocalService().maxPlayers
            ) {
                event.player.disconnect(
                    MiniMessage.miniMessage()
                        .deserialize(config.getEntry("proxy.fullKickMessage", ""))
                )
            }
        }
    }
}
