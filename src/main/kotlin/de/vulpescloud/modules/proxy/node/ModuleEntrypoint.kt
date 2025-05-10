package de.vulpescloud.modules.proxy.node

import de.vulpescloud.api.module.VulpesModule
import de.vulpescloud.api.virtualconfig.VirtualConfig
import de.vulpescloud.modules.proxy.node.commands.ProxyCommand
import de.vulpescloud.node.VulpesNode

class ModuleEntrypoint : VulpesModule {
    private lateinit var config: VirtualConfig

    override fun onDisable() {
        config.close()
    }

    override fun onEnable() {
        config = VirtualConfig("Proxy-Module")

        config.getEntry("motd.enabled", true)
        config.getEntry(
            "motd.firstLine",
            "<blue>A <color:#ff6600>VulpesCloud</color> hosted Network!</blue>",
        )
        config.getEntry("motd.secondLine", "<color:#ff6600>Proxy: %proxy%</color>")
        config.getEntry("maintenance.active", false)
        config.getEntry(
            "maintenance.firstLine",
            "<blue>A <color:#ff6600>VulpesCloud</color> hosted Network!</blue>",
        )
        config.getEntry("maintenance.secondLine", "<red>MAINTENANCE</red>")
        config.getEntry(
            "maintenance.kickMessage",
            "<gray>The Network is currently in <red>maintenance</red>!</gray>",
        )
        config.getEntry("maintenance.bypassPermission", "vulpescloud.proxy.maintenance.bypass")

        config.getEntry("proxy.fullKickMessage", "<red>Proxy is full!</red>")

        config.publish()

        VulpesNode.commandProvider.register(ProxyCommand(config))
        VulpesNode.eventManager.registerListener(EventListeners())
    }
}
