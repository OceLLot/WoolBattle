package net.ocejlot.woolbattle.items

import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.util.ItemAmount
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class ShootCrossbowListener : Listener {
    @EventHandler
    fun onArrowShoot(event: EntityShootBowEvent) {
        val player = event.entity
        if (player !is Player || event.bow?.type != Material.CROSSBOW)return

        val amount = 28
        if (ItemAmount.getPlayerItemCount(player, Material.RED_WOOL) < amount) {
            event.isCancelled = true
            return
        } else {
            WoolActions(player).reduceAmount(amount)
        }

        val arrow = event.projectile
        if (arrow is Arrow) {
            arrow.addPassenger(player)
        }
    }

}