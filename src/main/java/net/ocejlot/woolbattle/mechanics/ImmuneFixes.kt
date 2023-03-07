package net.ocejlot.woolbattle.mechanics

import net.ocejlot.woolbattle.util.IsWool
import net.ocejlot.woolbattle.wbDebugger
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerJoinEvent

class ImmuneFixes: Listener {
    @EventHandler
    fun onHungerChange(event: FoodLevelChangeEvent){
        event.isCancelled = true
    }

    @EventHandler
    fun onFallDamage(event: EntityDamageEvent){
        val player = event.entity

        if(player !is Player)return
        if(event.cause != DamageCause.FALL)return

        event.isCancelled = true
    }

    @EventHandler
    fun onAnyDamage(event: EntityDamageEvent){
        val player = event.entity

        if(player !is Player)return
        if(event.cause == DamageCause.FALL)return

        player.health = 20.0
    }

    @EventHandler
    fun onAnyBlockBreak(event: BlockBreakEvent){
        val player = event.player
        val block = event.block

        if(wbDebugger[player.uniqueId] == true)return
        if(IsWool(block.type).get())return

        event.isCancelled = true
    }
}
