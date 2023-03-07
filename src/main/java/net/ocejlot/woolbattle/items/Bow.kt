package net.ocejlot.woolbattle.items

import net.ocejlot.woolbattle.*
import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.util.IsWool
import net.ocejlot.woolbattle.util.ItemAmount
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent

class Bow: Listener {

    @EventHandler
    fun onArrowHitWool(event: ProjectileHitEvent){
        val arrow = event.entity
        if (arrow !is Arrow)return

        val block = event.hitBlock ?: return //Якщо null - return
        val location = block.location
        arrow.remove()

        if(arrow.type != EntityType.ARROW)return
        if(!IsWool(block.type).get())return

        if(!woolState.contains(location)){
            woolState[location] = true
        }else{
            val type = block.type
            woolState.remove(location)
            block.type = Material.AIR
            if(!playerPlacedBlockList.contains(location)){
                generatorBlockList.add(block.location)
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    block.type = type
                }, 10)
            }
        }
    }

    @EventHandler
    fun onBowShot(event: EntityShootBowEvent) {
        val player = event.entity
        if (player !is Player || event.bow?.type != Material.BOW)return

        val amount = 2
        if (ItemAmount.getPlayerWoolCount(player) < amount) {
            event.isCancelled = true
            return
        }else{
            WoolActions(player).reduceAmount(amount)
            arrows.add(event.projectile as Projectile)
        }
    }
}
