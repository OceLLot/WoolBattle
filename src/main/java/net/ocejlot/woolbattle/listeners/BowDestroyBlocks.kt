package net.ocejlot.woolbattle.listeners

import net.ocejlot.woolbattle.generatorBlockList
import net.ocejlot.woolbattle.playerPlacedBlockList
import net.ocejlot.woolbattle.util.IsWool
import net.ocejlot.woolbattle.woolState
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

class BowDestroyBlocks: Listener {

    @EventHandler
    fun onArrowHitWool(event: ProjectileHitEvent){
        val arrow = event.entity
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