package net.ocejlot.jakashuinya.jakashuinya.listeners


import net.ocejlot.jakashuinya.jakashuinya.generatorBlockList
import net.ocejlot.jakashuinya.jakashuinya.playerPlacedBlockList
import net.ocejlot.jakashuinya.jakashuinya.plugin
import net.ocejlot.jakashuinya.jakashuinya.wbDebugger
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class AddBlockToList: Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent){
        val block = event.block
        val uuid = event.player.uniqueId

        //Якщо увімкнений дебаг мод до нічого не додає і блок не зникне
        if(wbDebugger[uuid] == true)return

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