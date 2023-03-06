package net.ocejlot.woolbattle.listeners

import net.ocejlot.woolbattle.playerPlacedBlockList
import net.ocejlot.woolbattle.plugin
import net.ocejlot.woolbattle.slimeBlocks
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import kotlin.random.Random


class StepOnJumpPlatform : Listener {

    @EventHandler
    fun OnStep(event: PlayerMoveEvent){
        val player = event.player
        val location = player.location
        val world = player.world
        val bottomBlock: Block = world.getBlockAt(location.x.toInt(), (location.y - 1).toInt(), location.z.toInt())
        if(slimeBlocks.contains(bottomBlock.location)) {

            val randomVel = Random.nextInt(10, 40)
            player.sendMessage("$randomVel")
            val currentVelocity = player.velocity
            val newVelocity = Vector(currentVelocity.x, currentVelocity.y + randomVel, currentVelocity.z)
            player.velocity = newVelocity

            ClearPlatform(player.location.add(0.0, -1.0, 0.0))

            Bukkit.getScheduler().runTask(plugin, Runnable {
                slimeBlocks.toList().forEach() {
                    slimeBlocks.remove(it.block.location)
                }
            })
        }
    }

    fun ClearPlatform(location: Location){
        for (x in location.blockX - 2..location.blockX + 2) {
            for (y in location.blockY..location.blockY) {
                for (z in location.blockZ - 2..location.blockZ + 2) {
                    val blockLocation = Location(location.world, x.toDouble(), y.toDouble(), z.toDouble())
                    if(blockLocation.block.type == Material.SLIME_BLOCK){
                        playerPlacedBlockList.add(blockLocation)
                        blockLocation.block.type = Material.AIR
                    }
                }
            }
        }
    }
}