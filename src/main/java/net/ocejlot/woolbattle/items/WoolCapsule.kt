package net.ocejlot.woolbattle.items

import net.ocejlot.woolbattle.feachers.ItemFeatures
import net.ocejlot.woolbattle.feachers.WoolActions
import net.ocejlot.woolbattle.util.ItemAmount
import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class WoolCapsule : Listener {

    @EventHandler
    fun onRightClick(event: PlayerInteractEvent){
        val player = event.player
        val item = event.item
        val amount = 16

        if(event.hand != EquipmentSlot.HAND)return
        if(item != ItemStorage.woolCapsuleItem)return
        if(event.action == Action.LEFT_CLICK_AIR || event.action == Action.LEFT_CLICK_BLOCK)return
        if(ItemAmount.getPlayerItemCount(player, Material.RED_WOOL) < amount)return

        val location = player.location


        val roundedLocation = Location(player.world, player.location.blockX.toDouble() + 0.5, player.location.blockY.toDouble() + 0.5, player.location.blockZ.toDouble() + 0.5, player.location.yaw, player.location.pitch)
        player.teleport(roundedLocation)


        ItemFeatures().spawnWoolCapsule(location, Material.RED_WOOL)
        WoolActions(player).reduceAmount(16)
    }
}