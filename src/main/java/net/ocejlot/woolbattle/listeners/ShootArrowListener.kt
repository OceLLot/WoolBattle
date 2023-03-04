package net.ocejlot.woolbattle.listeners

import net.ocejlot.woolbattle.feachers.WoolActions
import net.ocejlot.woolbattle.util.ItemAmount
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class ShootArrowListener : Listener {
    @EventHandler
    fun OnArrowShoot(event: EntityShootBowEvent) {
        val player = event.entity
        if(player !is Player)return

        val amount = 2
        if (ItemAmount.getPlayerItemCount(player, Material.RED_WOOL) < amount) {
            event.isCancelled = true
            return
        }else{
            WoolActions(player).reduceAmount(amount)
        }

    }
}