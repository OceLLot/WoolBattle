package net.ocejlot.woolbattle.features

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import net.ocejlot.woolbattle.util.ItemAmount
import org.bukkit.GameMode
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleFlightEvent
import org.bukkit.util.Vector

class DoubleJump: Listener {
    @EventHandler
    fun onJump(event: PlayerJumpEvent){
        val player = event.player
        val amount = 5

        if(player.gameMode == GameMode.CREATIVE)return
        if(ItemAmount.getPlayerWoolCount(player) < amount)return

        player.allowFlight = true
    }



    @EventHandler
    fun onFlightToggle(event: PlayerToggleFlightEvent){
        val player = event.player
        val amount = 5

        if(player.gameMode == GameMode.CREATIVE)return
        event.isCancelled = true
        player.allowFlight = false

        if(player.foodLevel < 20)return

        WoolActions(player).reduceAmount(amount)

        player.foodLevel = 20-12

        val currentVelocity = player.velocity
        val newVelocity = Vector(currentVelocity.x, currentVelocity.y + 1.5, currentVelocity.z)
        player.velocity = newVelocity
    }
}