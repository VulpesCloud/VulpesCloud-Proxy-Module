package org.vulpesstudios.vulpescloud.modules.proxy.node

import build.buf.gen.vulpescloud.virtualconfig.v1.createVirtualConfigRequest
import org.vulpesstudios.vulpescloud.modules.proxy.common.config.ProxyModuleConfig
import org.vulpesstudios.vulpescloud.modules.proxy.node.commands.ProxyCommand
import org.vulpesstudios.vulpescloud.node.Node
import org.vulpesstudios.vulpescloud.node.modules.VulpesModule
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
                            _root_ide_package_.org.vulpesstudios.vulpescloud.modules.proxy.common.config.ProxyModuleConfig.serializer(),
                            _root_ide_package_.org.vulpesstudios.vulpescloud.modules.proxy.common.config.ProxyModuleConfig(),
                        )
                }
            )

            Node.instance.commandProvider.register(
                _root_ide_package_.org.vulpesstudios.vulpescloud.modules.proxy.node.commands.ProxyCommand(
                    this@ModuleEntrypoint
                )
            )
        }
    }

    suspend fun getConfig(): org.vulpesstudios.vulpescloud.modules.proxy.common.config.ProxyModuleConfig {
        return Node.instance.virtualConfigProvider.getCustomConfigObject("module_proxy")
            ?: throw Exception("Config is null!")
    }
}
