package net.ocejlot.woolbattle.feachers

import net.ocejlot.woolbattle.playerPlacedBlockList
import org.bukkit.Location
import org.bukkit.Material

class ItemFeatures {

    //слайм платформа
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

    //Кругла платформа
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

    //Капсула
    fun Location.getWoolCapsuleBlocks(): Array<Location> {
        val x = blockX
        val y = blockY
        val z = blockZ
        return arrayOf(
            Location(world, x - 1.0, y.toDouble(), z.toDouble()),
            Location(world, x + 1.0, y.toDouble(), z.toDouble()),
            Location(world, x.toDouble(), y.toDouble(), z - 1.0),
            Location(world, x.toDouble(), y.toDouble(), z + 1.0),
            Location(world, x - 1.0, y + 1.0, z.toDouble()),
            Location(world, x + 1.0, y + 1.0, z.toDouble()),
            Location(world, x.toDouble(), y + 1.0, z - 1.0),
            Location(world, x.toDouble(), y + 1.0, z + 1.0),
            Location(world, x.toDouble(), y - 1.0, z.toDouble()),
            Location(world, x.toDouble(), y + 2.0, z.toDouble())
        )
    }
    fun spawnWoolCapsule(location: Location, block: Material) {
        location.getWoolCapsuleBlocks().forEach {
            playerPlacedBlockList.add(it.block.location)
            if(it.block.type == Material.AIR) {
                it.block.type = block
            }
        }
    }
}


