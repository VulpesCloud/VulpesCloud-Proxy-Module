package de.vulpescloud.modules.proxy.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PostLoginEvent
import de.vulpescloud.modules.proxy.velocity.VelocityEntrypoint
import net.kyori.adventure.text.minimessage.MiniMessage

class PlayerJoinListener {
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
        }
    }
}
