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

    @Command("proxy set motd <enabled>")
    fun setMotd(source: CommandSource, @Argument("enabled") enabled: Boolean) {
        source.sendMessage("Setting MOTD to $enabled")
        config.setEntry("motd.enabled", enabled)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy set motd firstLine <line>")
    fun setMotdFirstLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting MOTD first line to $line")
        config.setEntry("motd.firstLine", line)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy set motd secondLine <line>")
    fun setMotdSecondLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting MOTD second line to $line")
        config.setEntry("motd.secondLine", line)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy set maintenance firstLine <line>")
    fun setMaintenanceFirstLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting Maintenance first line to $line")
        config.setEntry("maintenance.firstLine", line)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy set maintenance secondLine <line>")
    fun setMaintenanceSecondLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting Maintenance second line to $line")
        config.setEntry("maintenance.secondLine", line)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy set maintenance kickMessage <message>")
    fun setMaintenanceKickMessage(source: CommandSource, @Argument("message") message: String) {
        source.sendMessage("Setting Maintenance kick message to $message")
        config.setEntry("maintenance.kickMessage", message)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy set maintenance bypassPermission <permission>")
    fun setMaintenanceBypassPermission(source: CommandSource, @Argument("permission") permission: String) {
        source.sendMessage("Setting Maintenance bypass permission to $permission")
        config.setEntry("maintenance.bypassPermission", permission)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy set fullKickMessage <message>")
    fun setFullKickMessage(source: CommandSource, @Argument("message") message: String) {
        source.sendMessage("Setting Full kick message to $message")
        config.setEntry("proxy.fullKickMessage", message)
        config.publish()
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }

    @Command("proxy config reload")
    fun reloadConfig(source: CommandSource) {
        source.sendMessage("Reloading config")
        config.loadLocalChanges()
        config.publish()
        source.sendMessage("Config reloaded")
        getRC()?.sendMessage("CONFIG:UPDATED", ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name)
    }
}
