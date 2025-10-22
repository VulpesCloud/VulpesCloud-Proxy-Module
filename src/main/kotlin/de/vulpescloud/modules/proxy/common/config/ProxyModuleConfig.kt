package de.vulpescloud.modules.proxy.common.config

import de.vulpescloud.modules.proxy.common.config.sub.MaintenanceSubConfig
import de.vulpescloud.modules.proxy.common.config.sub.MotdSubConfig
import kotlinx.serialization.Serializable

@Serializable
data class ProxyModuleConfig(
    val motd: MotdSubConfig = MotdSubConfig(),
    val maintenance: MaintenanceSubConfig = MaintenanceSubConfig(),
    val fullKickMessage: String = "<red>This Server is currently full!</red>"
)
