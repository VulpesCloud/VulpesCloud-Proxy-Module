package de.vulpescloud.modules.proxy.node.commands

import de.vulpescloud.api.events.EventSerializer
import de.vulpescloud.modules.proxy.common.event.ProxyModuleConfigUpdateEvent
import de.vulpescloud.modules.proxy.node.ModuleEntrypoint
import de.vulpescloud.node.Node
import de.vulpescloud.node.NodeCoroutineScope
import de.vulpescloud.node.command.CommandSource
import de.vulpescloud.node.event.EventsService
import kotlinx.coroutines.launch
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command

@Suppress("unused")
class ProxyCommand(private val module: ModuleEntrypoint) {

    val configName = "module_proxy"

    @Command("proxy set maintenance enabled <enabled>")
    fun setMaintenance(source: CommandSource, @Argument("enabled") enabled: Boolean) {
        source.sendMessage("Setting Maintenance to $enabled")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(maintenance = config.maintenance.copy(active = enabled)),
            )
            notifyServices()
        }
    }

    @Command("proxy set motd <enabled>")
    fun setMotd(source: CommandSource, @Argument("enabled") enabled: Boolean) {
        source.sendMessage("Setting MOTD to $enabled")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(motd = config.motd.copy(enabled = enabled)),
            )
            notifyServices()
        }
    }

    @Command("proxy set motd firstLine <line>")
    fun setMotdFirstLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting MOTD first line to $line")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(motd = config.motd.copy(firstLine = line)),
            )
            notifyServices()
        }
    }

    @Command("proxy set motd secondLine <line>")
    fun setMotdSecondLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting MOTD second line to $line")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(motd = config.motd.copy(secondLine = line)),
            )
            notifyServices()
        }
    }

    @Command("proxy set maintenance firstLine <line>")
    fun setMaintenanceFirstLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting Maintenance first line to $line")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(maintenance = config.maintenance.copy(firstLine = line)),
            )
            notifyServices()
        }
    }

    @Command("proxy set maintenance secondLine <line>")
    fun setMaintenanceSecondLine(source: CommandSource, @Argument("line") line: String) {
        source.sendMessage("Setting Maintenance second line to $line")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(maintenance = config.maintenance.copy(secondLine = line)),
            )
            notifyServices()
        }
    }

    @Command("proxy set maintenance kickMessage <message>")
    fun setMaintenanceKickMessage(source: CommandSource, @Argument("message") message: String) {
        source.sendMessage("Setting Maintenance kick message to $message")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(maintenance = config.maintenance.copy(kickMessage = message)),
            )
            notifyServices()
        }
    }

    @Command("proxy set maintenance bypassPermission <permission>")
    fun setMaintenanceBypassPermission(
        source: CommandSource,
        @Argument("permission") permission: String,
    ) {
        source.sendMessage("Setting Maintenance bypass permission to $permission")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(maintenance = config.maintenance.copy(bypassPermission = permission)),
            )
            notifyServices()
        }
    }

    @Command("proxy set fullKickMessage <message>")
    fun setFullKickMessage(source: CommandSource, @Argument("message") message: String) {
        source.sendMessage("Setting Full kick message to $message")
        NodeCoroutineScope.launch {
            val config = module.getConfig()
            Node.instance.virtualConfigProvider.updateCustomConfig(
                configName,
                config.copy(fullKickMessage = message),
            )
            notifyServices()
        }
    }

    @Command("proxy config reload")
    fun reloadConfig(source: CommandSource) {
        source.sendMessage("Reloading config")
        NodeCoroutineScope.launch {
            Node.instance.virtualConfigProvider.updateDatabaseFromLocalConfig(configName)
            notifyServices()
        }
        source.sendMessage("Config reloaded")
    }

    private fun notifyServices() {
        EventsService.publish(
            EventSerializer.encode(ProxyModuleConfigUpdateEvent(System.currentTimeMillis())),
            true,
        )
    }
}
