package net.ocejlot.woolbattle.items

import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.util.ItemAmount
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class ThrowEnderPearlListener : Listener {

    @EventHandler

    fun onPlayerThrowEnderPearl(event: PlayerInteractEvent) {
        if (event.item?.type == Material.ENDER_PEARL) {
            val player = event.player
            val amount = 8
            WoolActions(player).reduceAmount(amount)
            if(ItemAmount.getPlayerWoolCount(player) < amount) {
                event.isCancelled = true
                return
            }

        }
    }
}




