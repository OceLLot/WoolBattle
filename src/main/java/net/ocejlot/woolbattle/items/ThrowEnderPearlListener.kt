package net.ocejlot.woolbattle.items

import net.ocejlot.woolbattle.features.ItemFeatures
import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.util.ItemAmount
import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class ThrowEnderPearlListener : Listener {
    @EventHandler
    fun onPlayerThrowEnderPearl(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return
        val amount = 8

        if(item != ItemStorage.enderpearl)return
        if(event.hand != EquipmentSlot.HAND)return
        if(event.action == Action.LEFT_CLICK_BLOCK || event.action == Action.LEFT_CLICK_AIR)return

        if(ItemAmount.getPlayerWoolCount(player) < amount) {
            event.isCancelled = true
            return
        }

        WoolActions(player).reduceAmount(amount)

        val slot = player.inventory.heldItemSlot
        ItemFeatures().itemCooldown(player, Material.FIREWORK_STAR, ItemStorage.enderpearl, 5, slot)
    }
}
