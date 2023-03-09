package net.ocejlot.woolbattle.items

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.ocejlot.woolbattle.features.ItemFeatures
import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.plugin
import net.ocejlot.woolbattle.util.ItemAmount
import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class SnowballSwapper: Listener {

    @EventHandler
    fun onSnowballThrow(event: PlayerInteractEvent){
        val player = event.player
        val item = event.item ?: return
        val amount = 16

        if(item != ItemStorage.swapSnowball)return
        if(event.hand != EquipmentSlot.HAND)return
        if(event.action == Action.LEFT_CLICK_BLOCK || event.action == Action.LEFT_CLICK_AIR)return

        if(ItemAmount.getPlayerWoolCount(player) < amount) {
            event.isCancelled = true
            return
        }

        WoolActions(player).reduceAmount(amount)

        //кд
        val slot = player.inventory.heldItemSlot
        ItemFeatures().itemCooldown(player, Material.SNOWBALL, ItemStorage.swapSnowball, 5, slot)
    }

    @EventHandler
    fun onSnowballHit(event: ProjectileHitEvent){
        val swapper = event.entity.shooter ?: return
        val swapped = event.hitEntity ?: return
        val snowball = event.entity

        if(swapper !is Player)return
        if(swapped !is Player)return
        if(snowball.type != EntityType.SNOWBALL)return

        swap(swapper, swapped)
    }

    private fun swap(swapper: Player, swapped: Player){
        val swapperLoc = swapper.location
        val swappedLoc = swapped.location

        swapper.teleport(swappedLoc)
        swapped.teleport(swapperLoc)
    }
}
