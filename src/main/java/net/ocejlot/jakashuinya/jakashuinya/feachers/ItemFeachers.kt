package net.ocejlot.jakashuinya.jakashuinya.feachers

import net.ocejlot.jakashuinya.jakashuinya.util.IsWool
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

    fun replaceBlocksWithAir(centerLocation: Location){
        for (x in centerLocation.blockX - 1..centerLocation.blockX + 1) {
            for (y in centerLocation.blockY..centerLocation.blockY + 2) {
                for (z in centerLocation.blockZ - 1..centerLocation.blockZ + 1) {
                    val blockLocation = Location(centerLocation.world, x.toDouble(), y.toDouble(), z.toDouble())
                    if(IsWool(blockLocation.block.type).get()) {
                        blockLocation.block.type = Material.AIR
                    }
                }
            }
        }
    }
}
