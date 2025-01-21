package de.vulpescloud.modules.proxy.node.commands

import de.vulpescloud.modules.proxy.node.FileManager
import de.vulpescloud.node.command.source.CommandSource
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.json.JSONObject


@Suppress("unused")
class ProxyCommand(
    private val configJson: JSONObject
) {

    @Command("proxy maintenance <enabled>")
    fun setMaintenance(
        source: CommandSource,
        @Argument("enabled") enabled: Boolean
    ) {
        configJson.getJSONObject("maintenance")
            .put("active", enabled)

        FileManager.updateAndPushConfig(configJson)
    }

}