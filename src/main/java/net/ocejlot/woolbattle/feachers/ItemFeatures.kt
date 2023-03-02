package net.ocejlot.woolbattle.feachers

import net.ocejlot.woolbattle.capsuleBlockList
import net.ocejlot.woolbattle.playerPlacedBlockList
import net.ocejlot.woolbattle.plugin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material

class ItemFeatures {

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

    fun spawnWoolCapsule(location: Location, block: Material) {
        capsuleBlockList.add(location.add(-1.0, 0.0, 0.0))
        capsuleBlockList.add(location.add(1.0, 0.0, 0.0))
        capsuleBlockList.add(location.add(0.0, 0.0, -1.0))
        capsuleBlockList.add(location.add(0.0, 0.0, 1.0))
        capsuleBlockList.add(location.add(-1.0, 1.0, 0.0))
        capsuleBlockList.add(location.add(1.0, 1.0, 0.0))
        capsuleBlockList.add(location.add(0.0, 1.0, -1.0))
        capsuleBlockList.add(location.add(0.0, 1.0, 1.0))
        capsuleBlockList.add(location.subtract(0.0, 1.0, 0.0))
        capsuleBlockList.add(location.add(0.0, 2.0, 0.0))

        Bukkit.getScheduler().runTask(plugin, Runnable {
            capsuleBlockList.toList().forEach {
                playerPlacedBlockList.add(it.block.location)
                it.block.type = block
            }
        })
    }
}



