package net.ocejlot.woolbattle

import net.ocejlot.woolbattle.commands.WoolBattleCommand
import net.ocejlot.woolbattle.items.SlimePlatform
import net.ocejlot.woolbattle.items.WoolCapsule
import net.ocejlot.woolbattle.items.WoolPlatform
import net.ocejlot.woolbattle.listeners.AddBlockToList
import net.ocejlot.woolbattle.listeners.BlockGenerator
import net.ocejlot.woolbattle.listeners.BowDestroyBlocks
import net.ocejlot.woolbattle.listeners.DeathEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

lateinit var plugin : Plugin
var wbDebugger = hashMapOf<UUID, Boolean>()
var playerPlacedBlockList = mutableListOf<Location>()
var generatorBlockList = mutableListOf<Location>()
var woolState = hashMapOf<Location, Boolean>()
var capsuleBlockList = mutableListOf<Location>()


class MainWoolBattle : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        registerEvents()
        registerCommands()
        logger.info("This peace of shit now is working")
    }

    override fun onDisable() {
        logger.warning("FUCK! Everything is fucked up")
    }

    private fun registerCommands(){
        getCommand("woolbattle")!!.setExecutor(WoolBattleCommand())
        getCommand("woolbattle")!!.tabCompleter = WoolBattleCommand()
    }

    private fun registerEvents(){
        Bukkit.getPluginManager().registerEvents(AddBlockToList(), plugin)
        Bukkit.getPluginManager().registerEvents(BlockGenerator(), plugin)
        Bukkit.getPluginManager().registerEvents(AddBlockToList(), plugin)
        Bukkit.getPluginManager().registerEvents(SlimePlatform(), plugin)
        Bukkit.getPluginManager().registerEvents(DeathEvent(), plugin)
        Bukkit.getPluginManager().registerEvents(BowDestroyBlocks(), plugin)
        Bukkit.getPluginManager().registerEvents(WoolPlatform(), plugin)
        Bukkit.getPluginManager().registerEvents(WoolCapsule(), plugin)
    }
}