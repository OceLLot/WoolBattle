package net.ocejlot.woolbattle.listeners

import net.ocejlot.woolbattle.slimeBlocks
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import java.util.*
import org.bukkit.util.Vector


class StepOnJumpPlatform : Listener {

    @EventHandler
    fun OnStep(event: PlayerMoveEvent){
        var player = event.player
        var location = player.location
        var world = player.world
        val bottomBlock: Block = world.getBlockAt(location.x.toInt(), (location.y - 1).toInt(), location.z.toInt())
        if(slimeBlocks.contains(bottomBlock.location)) {

            val currentVelocity = player.velocity
            val newVelocity = Vector(currentVelocity.x, currentVelocity.y + 3, currentVelocity.z)
            player.velocity = newVelocity


            slimeBlocks.forEach {
                slimeBlocks.remove(it.block.location)
                it.block.type = Material.AIR

            }
        }
    }
}