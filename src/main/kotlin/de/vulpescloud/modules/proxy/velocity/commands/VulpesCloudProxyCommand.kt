package de.vulpescloud.modules.proxy.velocity.commands

import com.mojang.brigadier.Command
import com.velocitypowered.api.command.BrigadierCommand
import de.vulpescloud.modules.proxy.velocity.VelocityEntrypoint
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.text.minimessage.MiniMessage

class VulpesCloudProxyCommand {
    val command =
        BrigadierCommand(
            BrigadierCommand.literalArgumentBuilder("vulpescloud-proxy")
                .requires { it.hasPermission("vulpescloud.proxy.commands") }
                .then(
                    BrigadierCommand.literalArgumentBuilder("reload")
                        .requires { it.hasPermission("vulpescloud.proxy.commands.reload") }
                        .executes { ctx ->
                            val source = ctx.source
                            source.sendMessage(
                                MiniMessage.miniMessage()
                                    .deserialize(
                                        "<grey>[<aqua>VulpesCloud-Proxy-Module</aqua>]</grey> Pulling new configuration!"
                                    )
                            )
                            runBlocking {
                                VelocityEntrypoint.bridgeAPI
                                    .getVirtualConfigAPI()
                                    .updateLocalConfigFromDatabase("module_proxy")
                            }
                            source.sendMessage(
                                MiniMessage.miniMessage()
                                    .deserialize(
                                        "<grey>[<aqua>VulpesCloud-Proxy-Module</aqua>]</grey> <gray>Successfully pulled new configuration!</gray>"
                                    )
                            )

                            Command.SINGLE_SUCCESS
                        }
                )
        )
}
