package net.ocejlot.jakashuinya.jakashuinya.listeners

import net.ocejlot.jakashuinya.jakashuinya.generatorBlockList
import net.ocejlot.jakashuinya.jakashuinya.woolState
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

        if(arrow.type != EntityType.ARROW)return
        if(generatorBlockList.contains(location))return

        if(!woolState.contains(location)){
            woolState[location] = true
        }else{
            woolState.remove(location)
            block.type = Material.AIR
        }
        arrow.remove()
    }
}