package net.ocejlot.jakashuinya.jakashuinya.listeners

import net.ocejlot.jakashuinya.jakashuinya.playerPlacedBlockList
import net.ocejlot.jakashuinya.jakashuinya.plugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class DeathEvent: Listener {
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