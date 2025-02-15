package de.vulpescloud.modules.proxy.velocity.listener

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PostLoginEvent
import de.vulpescloud.modules.proxy.velocity.VelocityEntrypoint
import net.kyori.adventure.text.minimessage.MiniMessage

class PlayerJoinListener {
    @Subscribe
    fun onPlayerJoin(event: PostLoginEvent) {
        val jsonConfig = VelocityEntrypoint.instance.configJson
        val maintenanceJSON = jsonConfig.getJSONObject("maintenance")
        if (maintenanceJSON.getBoolean("active")) {
            if (!event.player.hasPermission(maintenanceJSON.getString("bypassPermission"))) {
                event.player.disconnect(MiniMessage.miniMessage().deserialize(maintenanceJSON.getString("kickMessage")))
            }
        }
    }

}