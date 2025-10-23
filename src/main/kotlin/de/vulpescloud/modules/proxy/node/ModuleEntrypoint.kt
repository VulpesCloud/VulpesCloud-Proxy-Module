package de.vulpescloud.modules.proxy.node

import build.buf.gen.vulpescloud.virtualconfig.v1.createVirtualConfigRequest
import de.vulpescloud.modules.proxy.common.config.ProxyModuleConfig
import de.vulpescloud.modules.proxy.node.commands.ProxyCommand
import de.vulpescloud.node.Node
import de.vulpescloud.node.modules.VulpesModule
import kotlinx.coroutines.runBlocking

class ModuleEntrypoint : VulpesModule {

    override fun onDisable() {}

    override fun onLoad() {}

    override fun onUnload() {}

    override fun onEnable() {
        runBlocking {
            Node.instance.localGrpcClient.virtualConfigAPI.createVirtualConfig(
                createVirtualConfigRequest {
                    this.name = "module_proxy"
                    this.config =
                        Node.instance.virtualConfigProvider.json.encodeToString(
                            ProxyModuleConfig.serializer(),
                            ProxyModuleConfig(),
                        )
                }
            )

            Node.instance.commandProvider.register(ProxyCommand(this@ModuleEntrypoint))
        }
    }

    suspend fun getConfig(): ProxyModuleConfig {
        return Node.instance.virtualConfigProvider.getCustomConfigObject("module_proxy")
            ?: throw Exception("Config is null!")
    }
}
