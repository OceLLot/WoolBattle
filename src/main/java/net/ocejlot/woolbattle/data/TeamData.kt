package net.ocejlot.woolbattle.data

import org.bukkit.entity.Player

data class TeamData(
        val name: String,
        val members: MutableList<Player>
)
