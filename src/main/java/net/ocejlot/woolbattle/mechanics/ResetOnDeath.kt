package net.ocejlot.woolbattle.mechanics

import net.ocejlot.woolbattle.playerPlacedBlockList
import net.ocejlot.woolbattle.plugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class ResetOnDeath: Listener {

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        Bukkit.getScheduler().runTask(plugin, Runnable {
            playerPlacedBlockList.toList().forEach {
                playerPlacedBlockList.remove(it.block.location)
                it.block.type = Material.AIR
            }
        })
    }
}