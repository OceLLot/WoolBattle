package net.ocejlot.woolbattle.util

import org.bukkit.entity.Player

object ItemAmount {
    fun getPlayerWoolCount(player: Player): Int {
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
