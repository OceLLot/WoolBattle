package net.ocejlot.woolbattle.items

import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.generatorBlockList
import net.ocejlot.woolbattle.playerPlacedBlockList
import net.ocejlot.woolbattle.util.IsWool
import net.ocejlot.woolbattle.util.ItemAmount
import net.ocejlot.woolbattle.woolState
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent

class Bow: Listener {

    @EventHandler
    fun onArrowHitWool(event: ProjectileHitEvent){
        if (event.entity is Arrow) {
            val arrow = event.entity as Arrow

            val block = event.hitBlock ?: return //Якщо null - return
            val location = block.location
            arrow.remove()

            if(arrow.type != EntityType.ARROW)return
            if(!IsWool(block.type).get())return

            //Провірка, чи не є часом цей блок поставленим гравцем.
            if(playerPlacedBlockList.contains(location)){
                playerPlacedBlockList.remove(location)
                return}

            //Провірка, чи не є часом цей блок - вперше зломаним блоком генератора
            if(!generatorBlockList.contains(location)){
                generatorBlockList.add(location)
            }

            if(!woolState.contains(location)){
                woolState[location] = true
            }else{
                woolState.remove(location)
                block.type = Material.AIR
            }
        }
    }



    @EventHandler
    fun onBowShot(event: EntityShootBowEvent) {
        val player = event.entity
        if (player !is Player || event.bow?.type != Material.BOW)return

        val amount = 2
        if (ItemAmount.getPlayerItemCount(player, Material.RED_WOOL) < amount) {
            event.isCancelled = true
            return
        }else{
            WoolActions(player).reduceAmount(amount)
        }
    }
}