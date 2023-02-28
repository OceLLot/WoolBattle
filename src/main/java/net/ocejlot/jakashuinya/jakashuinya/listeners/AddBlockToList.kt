package net.ocejlot.jakashuinya.jakashuinya.listeners


import net.ocejlot.jakashuinya.jakashuinya.MapManager.removeExpiredBlocks
import net.ocejlot.jakashuinya.jakashuinya.blockTimer
import net.ocejlot.jakashuinya.jakashuinya.generatorBlockList
import net.ocejlot.jakashuinya.jakashuinya.playerPlacedBlockList
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class AddBlockToList: Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent){

        removeExpiredBlocks()

        val block = event.block
        if(generatorBlockList.contains(block.location)){
            event.isCancelled = true
            return
        }

        val player = event.player
        val time = System.currentTimeMillis() / 10 + 12000
        if(generatorBlockList.contains(block.location))
        blockTimer[time] = block.location

        player.sendMessage(time.toString())
        playerPlacedBlockList.add(block.location)

    }
}