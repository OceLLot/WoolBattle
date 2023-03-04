package net.ocejlot.woolbattle.util

import org.bukkit.Material
import org.bukkit.entity.Player

object ItemAmount {
    fun getPlayerItemCount(player: Player, material: Material): Int {
        var count = 0
        val inventory = player.inventory
        for (item in inventory) {
            if (item != null && IsWool(item.type).get()) {
                count += item.amount
            }
        }
        return count
    }
}

