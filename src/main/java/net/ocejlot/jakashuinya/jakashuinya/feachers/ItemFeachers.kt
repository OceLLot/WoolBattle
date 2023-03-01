package net.ocejlot.jakashuinya.jakashuinya.feachers

import net.ocejlot.jakashuinya.jakashuinya.playerPlacedBlockList
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
                        playerPlacedBlockList.add(blockLocation)
                        blockLocation.block.type = block
                    }
                }
            }
        }
    }

    fun spawnRoundPlatform(location: Location, block: Material){
        val centerBlock = location.block
        for (x in centerBlock.x - 4..centerBlock.x + 4) {
            for (y in centerBlock.y..centerBlock.y) {
                for (z in centerBlock.z - 4..centerBlock.z + 4) {
                    val blockLocation = Location(location.world, x.toDouble(), y.toDouble(), z.toDouble())
                    if(blockLocation.distance(centerBlock.location) <= 4 && blockLocation.block.type == Material.AIR){
                        playerPlacedBlockList.add(blockLocation)
                        blockLocation.block.type = block
                    }
                }
            }
        }
    }
}
