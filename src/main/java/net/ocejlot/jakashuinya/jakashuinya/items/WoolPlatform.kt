package net.ocejlot.jakashuinya.jakashuinya.items

import net.ocejlot.jakashuinya.jakashuinya.feachers.ItemFeachers
import net.ocejlot.jakashuinya.jakashuinya.feachers.WoolActions
import net.ocejlot.jakashuinya.jakashuinya.util.ItemAmount
import net.ocejlot.jakashuinya.jakashuinya.util.ItemStorage
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class WoolPlatform : Listener{

    @EventHandler
    fun onRightClick(event: PlayerInteractEvent){
        val player = event.player
        val item = event.item
        val amount = 16

        if(event.hand != EquipmentSlot.HAND)return
        if(item != ItemStorage.woolRoundPlatformItem)return
        if(event.action == Action.LEFT_CLICK_AIR || event.action == Action.LEFT_CLICK_BLOCK)return
        if(ItemAmount.getPlayerItemCount(player, Material.RED_WOOL) < amount)return

        val location = player.location.add(0.0, -6.0, 0.0)
        ItemFeachers().spawnRoundPlatform(location, Material.WHITE_WOOL)
        WoolActions(player).reduceAmount(16)
    }
}