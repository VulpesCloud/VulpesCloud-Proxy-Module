package de.vulpescloud.modules.proxy.common.config.sub

import kotlinx.serialization.Serializable

@Serializable
data class MotdSubConfig(
    val enabled: Boolean = true,
    val firstLine: String = "<blue>A <color:#ff6600>VulpesCloud</color> hosted Network!</blue>",
    val secondLine: String = "<color:#ff6600>Proxy: %proxy%</color>"
)
