package net.ocejlot.woolbattle.data

import org.bukkit.Material
import org.bukkit.entity.Player

data class PlayerData(
        var team: TeamData,
        var woolColor: Material,
        var lastPunchedPlayer: Player? = null,
        var lastPunchedTime: Int? = null
)
