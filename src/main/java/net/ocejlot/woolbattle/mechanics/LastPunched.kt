package net.ocejlot.woolbattle.mechanics

import net.ocejlot.woolbattle.data.GameStates
import net.ocejlot.woolbattle.gameState
import net.ocejlot.woolbattle.playerData
import net.ocejlot.woolbattle.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileHitEvent

class LastPunched: Listener {

    @EventHandler
    fun onBowDamage(event: ProjectileHitEvent){
        val arrow = event.entity
        if (arrow !is Arrow)return

        val victim = event.hitEntity ?: return
        val attacker = event.entity.shooter

        if(victim !is Player)return
        if(attacker !is Player)return
        if(playerData[victim.uniqueId]!!.lastPunchedPlayer != null)return
        if(gameState == GameStates.LOBBY){
            event.isCancelled = true
            return
        }

        playerData[victim.uniqueId]!!.lastPunchedPlayer = attacker

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            playerData[victim.uniqueId]!!.lastPunchedPlayer = null
        }, 15*20)
    }

    @EventHandler
    fun onPunch(event: EntityDamageByEntityEvent){
        val victim = event.entity
        val attacker = event.damager

        if(victim !is Player)return
        if(attacker !is Player)return
        if(playerData[victim.uniqueId]!!.lastPunchedPlayer != null)return
        if(gameState == GameStates.LOBBY){
            event.isCancelled = true
            return
        }

        playerData[victim.uniqueId]!!.lastPunchedPlayer = attacker
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            playerData[victim.uniqueId]!!.lastPunchedPlayer = null
        }, 15*20)
    }
}
