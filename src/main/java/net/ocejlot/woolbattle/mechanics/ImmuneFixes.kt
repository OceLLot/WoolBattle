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
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class ImmuneFixes: Listener {

    @EventHandler
    fun onFallDamage(event: EntityDamageEvent){
        val player = event.entity

        if(player !is Player)return
        if(event.cause != DamageCause.FALL)return

        event.isCancelled = true
    }

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent){
        if(wbDebugger[event.player.uniqueId] == true)return

        event.isCancelled = true
    }

    @EventHandler
    fun onHandSwap(event: PlayerSwapHandItemsEvent){
        if(wbDebugger[event.player.uniqueId] == true)return

        event.isCancelled = true
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent){
        if(wbDebugger[event.whoClicked.uniqueId] == true)return

        event.isCancelled = true
    }

    @EventHandler
    fun onCooldownItemClick(event: PlayerInteractEvent){
        val item = event.item ?: return
        val name = item.itemMeta.displayName() ?: return

        if(name.toString().contains("Перезарядка")){
            event.isCancelled = true
            return
        }

    }

    @EventHandler
    fun onAnyDamage(event: EntityDamageEvent){
        val player = event.entity

        if(player !is Player)return
        if(event.cause == DamageCause.FALL)return
        if(event.cause == DamageCause.VOID)return

        event.damage = 0.0
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
