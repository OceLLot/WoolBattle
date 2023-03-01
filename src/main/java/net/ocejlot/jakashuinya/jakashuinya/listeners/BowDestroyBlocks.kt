package net.ocejlot.jakashuinya.jakashuinya.listeners

import net.ocejlot.jakashuinya.jakashuinya.feachers.ItemFeachers
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

        ItemFeachers().replaceBlocksWithAir(location)
        arrow.remove()
    }
}