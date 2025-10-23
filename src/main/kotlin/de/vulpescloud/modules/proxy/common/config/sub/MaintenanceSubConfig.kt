package de.vulpescloud.modules.proxy.common.config.sub

import kotlinx.serialization.Serializable

@Serializable
data class MaintenanceSubConfig(
    val active: Boolean = false,
    val firstLine: String = "<blue>A <color:#ff6600>VulpesCloud</color> hosted Network!</blue>",
    val secondLine: String = "<red>MAINTENANCE</red>",
    val kickMessage: String = "<gray>The Network is currently in <red>maintenance</red>!</gray>",
    val bypassPermission: String = "vulpescloud.proxy.maintenance.bypass"
)
