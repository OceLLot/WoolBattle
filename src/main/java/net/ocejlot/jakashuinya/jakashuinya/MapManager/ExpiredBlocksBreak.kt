package net.ocejlot.jakashuinya.jakashuinya.MapManager

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import java.util.*


class ExpiredBlocksBreak: Listener

    // Create a map to store the time that each block was broken
    val blockBreakTimes = HashMap<Block, Long>()

    // When a block is broken, add its time to the map
    @EventHandler
    fun onPlace(event: Event) {
        val block = event.
        val currentTime = System.currentTimeMillis()
        blockBreakTimes[block] = currentTime
    }

    // Periodically check the current time and remove any blocks that have been broken for more than 2 minutes
    fun removeExpiredBlocks() {
        val currentTime = System.currentTimeMillis()
        val expiredBlocks = ArrayList<Block>()
        for ((block, breakTime) in blockBreakTimes) {
            if (currentTime - breakTime > 10000) { // 120000 milliseconds = 2 minutes
                expiredBlocks.add(block)
            }
        }
        for (block in expiredBlocks) {
            blockBreakTimes.remove(block)
            block.type = Material.AIR // Remove the block from the game world
    }
}