package org.vulpesstudios.vulpescloud.modules.proxy.common.config

import org.vulpesstudios.vulpescloud.modules.proxy.common.config.sub.MaintenanceSubConfig
import org.vulpesstudios.vulpescloud.modules.proxy.common.config.sub.MotdSubConfig
import kotlinx.serialization.Serializable

@Serializable
data class ProxyModuleConfig(
    val motd: org.vulpesstudios.vulpescloud.modules.proxy.common.config.sub.MotdSubConfig = _root_ide_package_.org.vulpesstudios.vulpescloud.modules.proxy.common.config.sub.MotdSubConfig(),
    val maintenance: org.vulpesstudios.vulpescloud.modules.proxy.common.config.sub.MaintenanceSubConfig = _root_ide_package_.org.vulpesstudios.vulpescloud.modules.proxy.common.config.sub.MaintenanceSubConfig(),
    val fullKickMessage: String = "<red>This Server is currently full!</red>"
)
