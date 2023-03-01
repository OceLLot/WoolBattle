package net.ocejlot.jakashuinya.jakashuinya.feachers

import org.bukkit.Location
import org.bukkit.Material

class ItemFeachers {

    fun spawnPlatform(location: Location, block: Material){
        for (x in location.blockX - 1..location.blockX + 1) {
            for (y in location.blockY..location.blockY) {
                for (z in location.blockZ - 1..location.blockZ + 1) {
                    val blockLocation = Location(location.world, x.toDouble(), y.toDouble(), z.toDouble())
                    if(blockLocation.block.type == Material.AIR){
                        blockLocation.block.type = block
                    }
                }
            }
        }
    }
}
