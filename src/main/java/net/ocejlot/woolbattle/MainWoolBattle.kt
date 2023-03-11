package net.ocejlot.woolbattle

import net.ocejlot.woolbattle.items.ThrowEnderPearlListener
import net.ocejlot.woolbattle.commands.WoolBattleCommand
import net.ocejlot.woolbattle.data.GameStates
import net.ocejlot.woolbattle.data.PlayerData
import net.ocejlot.woolbattle.data.PlayerPerksData
import net.ocejlot.woolbattle.features.DoubleJump
import net.ocejlot.woolbattle.items.Bow
import net.ocejlot.woolbattle.items.ShootCrossbowListener
import net.ocejlot.woolbattle.items.SnowballSwapper
import net.ocejlot.woolbattle.items.custom.*
import net.ocejlot.woolbattle.mapmenager.GameManager
import net.ocejlot.woolbattle.mapmenager.ResetOnDeath
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

var playerData = hashMapOf<UUID, PlayerData>()
var playerPerksData = hashMapOf<UUID, PlayerPerksData>()

lateinit var gameState: GameStates

class MainWoolBattle : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        registerEvents()
        registerCommands()
        saveDefaultConfig()
        gameState = GameStates.LOBBY
        ResetOnDeath().playerYCoordinate()
        logger.info("WoolBattle loaded")
    }

    private fun registerCommands(){
        getCommand("woolbattle")!!.setExecutor(WoolBattleCommand())
        getCommand("woolbattle")!!.tabCompleter = WoolBattleCommand()
    }

    private fun registerEvents(){
        val plManager = Bukkit.getPluginManager()

        plManager.registerEvents(WoolListManager(), plugin)
        plManager.registerEvents(WoolGenerator(), plugin)
        plManager.registerEvents(WoolListManager(), plugin)
        plManager.registerEvents(SlimePlatform(), plugin)
        plManager.registerEvents(JumpPlatform(), plugin)
        plManager.registerEvents(ResetOnDeath(), plugin)
        plManager.registerEvents(Bow(), plugin)
        plManager.registerEvents(WoolPlatform(), plugin)
        plManager.registerEvents(WoolCapsule(), plugin)
        plManager.registerEvents(ThrowEnderPearlListener(), plugin)
        plManager.registerEvents(ShootCrossbowListener(), plugin)
        plManager.registerEvents(StepOnJumpPlatform(), plugin)
        plManager.registerEvents(GameManager(), plugin)
        plManager.registerEvents(VanillaMechanicsFix(), plugin)
        plManager.registerEvents(SnowballSwapper(), plugin)
        plManager.registerEvents(DoubleJump(), plugin)
        plManager.registerEvents(KitPickerItem(), plugin)
        plManager.registerEvents(LastPunched(), plugin)
    }
}
