package net.ocejlot.woolbattle.mechanics

import net.ocejlot.woolbattle.features.WoolActions
import net.ocejlot.woolbattle.generatorBlockList
import net.ocejlot.woolbattle.playerPlacedBlockList
import net.ocejlot.woolbattle.plugin
import net.ocejlot.woolbattle.util.IsWool
import net.ocejlot.woolbattle.util.ItemAmount
import net.ocejlot.woolbattle.wbDebugger
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class WoolGenerator: Listener {
    
    @EventHandler
    fun breakEvent(event: BlockBreakEvent) {
        val block = event.block
        val player = event.player

        //Якщо увімкнений дебаг мод то можна ламати генератори
        if(wbDebugger[player.uniqueId] == true)return

        //Провірка на те, чи є блок в списку блоків та очистка дропів.
        if (IsWool(block.type).get())event.isDropItems = false
        else return

        //Провірка, чи досягнув гравець ліміту блоків
        val itemAmount = ItemAmount.getPlayerWoolCount(player)
        val stackCount = 3
        if (itemAmount < (64*stackCount)) {
            WoolActions(player).addAmount(1)
        }



        val type = block.type

        //Провірка, чи не є часом цей блок поставленим гравцем.
        if(playerPlacedBlockList.contains(block.location)){
            playerPlacedBlockList.remove(block.location)
            return}

        //Провірка, чи не є часом цей блок - вперше зломаним блоком генератора
        if(!generatorBlockList.contains(block.location)){
            generatorBlockList.add(block.location)
        }

        //Встанговлення блока з затримкою на місці генераторів
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            block.type = type
        }, 10)
    }
}
