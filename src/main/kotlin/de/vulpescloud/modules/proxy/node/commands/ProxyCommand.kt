package de.vulpescloud.modules.proxy.node.commands

import de.vulpescloud.api.virtualconfig.VirtualConfig
import de.vulpescloud.jediswrapper.JedisWrapper.getRC
import de.vulpescloud.modules.proxy.common.ProxyModuleChannels
import de.vulpescloud.node.command.CommandSource
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command

@Suppress("unused")
class ProxyCommand(private val config: VirtualConfig) {

    @Command("proxy set maintenance <enabled>")
    fun setMaintenance(source: CommandSource, @Argument("enabled") enabled: Boolean) {
        source.sendMessage("Setting Maintenance to $enabled")
        config.setEntry("maintenance.active", enabled)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }
}
