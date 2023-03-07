package net.ocejlot.woolbattle.mechanics


import net.ocejlot.woolbattle.generatorBlockList
import net.ocejlot.woolbattle.playerPlacedBlockList
import net.ocejlot.woolbattle.plugin
import net.ocejlot.woolbattle.util.IsWool
import net.ocejlot.woolbattle.wbDebugger
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class WoolListManager: Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent){
        val block = event.block
        val uuid = event.player.uniqueId

        //Якщо увімкнений дебаг мод до нічого не додає і блок не зникне
        if(wbDebugger[uuid] == true)return

        //Перевірка на вовну
        if(!IsWool(block.type).get())return

        //Чи не поставив гравець блок на місце генератора
        if(generatorBlockList.contains(block.location)){
            event.isCancelled = true
            return
        }

        //Додаємо корди блоки у список
        playerPlacedBlockList.add(block.location)

        //Через 2 хв ставить блок на повітря
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            block.type = Material.AIR
        }, 20*2*60)//затримка 2 хв
    }
}