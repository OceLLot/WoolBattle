package net.ocejlot.woolbattle

import net.ocejlot.woolbattle.items.ThrowEnderPearlListener
import net.ocejlot.woolbattle.commands.WoolBattleCommand
import net.ocejlot.woolbattle.items.Bow
import net.ocejlot.woolbattle.items.ShootCrossbowListener
import net.ocejlot.woolbattle.items.custom.JumpPlatform
import net.ocejlot.woolbattle.items.custom.SlimePlatform
import net.ocejlot.woolbattle.items.custom.WoolCapsule
import net.ocejlot.woolbattle.items.custom.WoolPlatform
import net.ocejlot.woolbattle.mechanics.*
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
var slimeBlocks = mutableListOf<Location>()



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
        Bukkit.getPluginManager().registerEvents(WoolListManager(), plugin)
        Bukkit.getPluginManager().registerEvents(WoolGenerator(), plugin)
        Bukkit.getPluginManager().registerEvents(WoolListManager(), plugin)
        Bukkit.getPluginManager().registerEvents(SlimePlatform(), plugin)
        Bukkit.getPluginManager().registerEvents(JumpPlatform(), plugin)
        Bukkit.getPluginManager().registerEvents(ResetOnDeath(), plugin)
        Bukkit.getPluginManager().registerEvents(Bow(), plugin)
        Bukkit.getPluginManager().registerEvents(WoolPlatform(), plugin)
        Bukkit.getPluginManager().registerEvents(WoolCapsule(), plugin)
        Bukkit.getPluginManager().registerEvents(ThrowEnderPearlListener(), plugin)
        Bukkit.getPluginManager().registerEvents(ShootCrossbowListener(), plugin)
        Bukkit.getPluginManager().registerEvents(StepOnJumpPlatform(), plugin)

    }
}
