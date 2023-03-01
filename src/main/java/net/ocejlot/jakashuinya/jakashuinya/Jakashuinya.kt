package net.ocejlot.jakashuinya.jakashuinya

import net.ocejlot.jakashuinya.jakashuinya.commands.WoolBattleCommand
import net.ocejlot.jakashuinya.jakashuinya.listeners.AddBlockToList
import net.ocejlot.jakashuinya.jakashuinya.listeners.BlockGenerator
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

lateinit var plugin : Plugin
var wbDebugger = hashMapOf<UUID, Boolean>()
var blockTimer = hashMapOf<Long, Location>()
var playerPlacedBlockList = mutableListOf<Location>()
var generatorBlockList = mutableListOf<Location>()

class Jakashuinya : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        registerEvents()
        registerCommands()
        logger.info("This peace of shit now is working")
    }

    override fun onDisable() {
        logger.warning("FUCK! Everything is fucked up")
    }

    fun registerCommands(){
        getCommand("woolbattle")!!.setExecutor(WoolBattleCommand())
        getCommand("woolbattle")!!.tabCompleter = WoolBattleCommand()
    }

    fun registerEvents(){
        Bukkit.getPluginManager().registerEvents(AddBlockToList(), plugin)
        Bukkit.getPluginManager().registerEvents(BlockGenerator(), plugin)
        Bukkit.getPluginManager().registerEvents(AddBlockToList(), plugin)
        //Bukkit.getPluginManager().registerEvents(ExpiredBlocksBreak(), plugin)
    }
}

