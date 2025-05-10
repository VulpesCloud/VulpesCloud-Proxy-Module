package de.vulpescloud.modules.proxy.velocity

import com.velocitypowered.api.proxy.ProxyServer
import de.vulpescloud.api.virtualconfig.VirtualConfig
import de.vulpescloud.jediswrapper.redis.ChannelListener
import de.vulpescloud.modules.proxy.common.ProxyModuleChannels
import net.kyori.adventure.text.minimessage.MiniMessage

class RedisChannelListener(
    private val config: VirtualConfig,
    private val proxyServer: ProxyServer,
) : ChannelListener(ProxyModuleChannels.VULPESCLOUD_MODULES_PROXY.name) {

    override fun onMessage(message: String) {
        if (message.contains("CONFIG:UPDATED")) {
            proxyServer.consoleCommandSource.sendMessage(
                MiniMessage.miniMessage()
                    .deserialize(
                        "<grey>[<aqua>VulpesCloud-Proxy-Module</aqua>]</grey> Pulling new configuration!"
                    )
            )
            config.pull()
        }
    }
}
