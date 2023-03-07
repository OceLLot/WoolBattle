package net.ocejlot.woolbattle.mechanics

import net.ocejlot.woolbattle.mapmenager.Scoreboard
import net.ocejlot.woolbattle.mapmenager.blueTeam
import net.ocejlot.woolbattle.mapmenager.redTeam
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
        val player = event.player
        if(redTeam.members.contains(player)){
            redTeam.lives--
        }
        if(blueTeam.members.contains(player)){
            blueTeam.lives--
        }

        Scoreboard().scoreboard()
        Bukkit.getOnlinePlayers().forEach{
            Scoreboard().display(it)
        }

        Bukkit.getScheduler().runTask(plugin, Runnable {
            playerPlacedBlockList.toList().forEach {
                playerPlacedBlockList.remove(it.block.location)
                it.block.type = Material.AIR
            }
        })
    }
}
