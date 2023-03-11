package net.ocejlot.woolbattle.items.custom

import net.ocejlot.woolbattle.features.ItemFeatures
import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.playerData
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
        if(ItemAmount.getPlayerWoolCount(player) < amount)return

        val location = player.location

        event.isCancelled = true

        val roundedLocation = Location(player.world, player.location.blockX.toDouble() + 0.5, player.location.blockY.toDouble() + 0.5, player.location.blockZ.toDouble() + 0.5, player.location.yaw, player.location.pitch)
        player.teleport(roundedLocation)

        val slot = player.inventory.heldItemSlot

        ItemFeatures().itemCooldown(player, Material.GRAY_STAINED_GLASS, ItemStorage.woolCapsuleItem, 20, slot)
        ItemFeatures().spawnWoolCapsule(location, playerData[player.uniqueId]!!.woolColor)
        WoolActions(player).reduceAmount(16)
    }
}