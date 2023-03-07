package net.ocejlot.woolbattle.items

import net.ocejlot.woolbattle.plugin
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

        if(item.type != Material.SNOWBALL)return
        if(item != ItemStorage.swapSnowball)return
        if(event.hand != EquipmentSlot.HAND)return
        if(event.action == Action.LEFT_CLICK_BLOCK || event.action == Action.LEFT_CLICK_BLOCK)return

        val inventory = player.inventory
        val slot = inventory.heldItemSlot
        var count = 5

        var taskId = Random.nextInt()
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            if(count <= 0) {  //if looped 5 times do:
                inventory.setItem(slot, ItemStorage.swapSnowball)
                Bukkit.getScheduler().cancelTask(taskId)  //cancel loop
                return@scheduleSyncRepeatingTask
            }
            inventory.setItem(slot, ItemStack(Material.SNOWBALL, count))
            count--  //-1 from timers
        },20,20)  //20 ticks delay
    }

    @EventHandler
    fun onSnowballHit(event: ProjectileHitEvent){
        val swapper = event.entity.shooter as Player
        val swapped = event.hitEntity ?: return
        val snowball = event.entity

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
