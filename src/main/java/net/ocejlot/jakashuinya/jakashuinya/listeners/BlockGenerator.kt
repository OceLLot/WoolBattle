package net.ocejlot.jakashuinya.jakashuinya.listeners

import net.ocejlot.jakashuinya.jakashuinya.generatorBlockList
import net.ocejlot.jakashuinya.jakashuinya.playerPlacedBlockList
import net.ocejlot.jakashuinya.jakashuinya.plugin
import net.ocejlot.jakashuinya.jakashuinya.util.ItemAmount
import net.ocejlot.jakashuinya.jakashuinya.wbDebugger
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

class BlockGenerator: Listener {
    
    @EventHandler
    fun breakEvent(event: BlockBreakEvent) {
        val block = event.block
        val player = event.player


        //Якщо увімкнений дебаг мод то можна ламати генератори
        if(wbDebugger[player.uniqueId] == true)return

        //Провірка на те, чи є блок в списку блоків та очистка дропів.
        val list = listOf(Material.RED_WOOL, Material.BLUE_WOOL)
        if (list.contains(block.type)) {
            event.isDropItems = false
        }
        else return


        //Провірка, чи досягнув гравець ліміту блоків
        val itemAmount = ItemAmount.getPlayerItemCount(player, Material.RED_WOOL)
        val stackCount = 3
        if (itemAmount <= (64* stackCount)-1) {
            player.inventory.addItem(ItemStack(Material.RED_WOOL))
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