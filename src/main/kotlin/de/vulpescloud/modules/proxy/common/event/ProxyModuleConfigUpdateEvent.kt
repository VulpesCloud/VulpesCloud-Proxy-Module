package de.vulpescloud.modules.proxy.common.event

import kotlinx.serialization.Serializable

@Serializable
data class ProxyModuleConfigUpdateEvent(
    val timestamp: Long,
)
