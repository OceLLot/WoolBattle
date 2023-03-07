package net.ocejlot.woolbattle

import net.ocejlot.woolbattle.items.ThrowEnderPearlListener
import net.ocejlot.woolbattle.commands.WoolBattleCommand
import net.ocejlot.woolbattle.items.Bow
import net.ocejlot.woolbattle.items.ShootCrossbowListener
import net.ocejlot.woolbattle.items.custom.JumpPlatform
import net.ocejlot.woolbattle.items.custom.SlimePlatform
import net.ocejlot.woolbattle.items.custom.WoolCapsule
import net.ocejlot.woolbattle.items.custom.WoolPlatform
import net.ocejlot.woolbattle.mapmenager.GameStart
import net.ocejlot.woolbattle.mechanics.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Projectile
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

lateinit var plugin : Plugin
var wbDebugger = hashMapOf<UUID, Boolean>()
var playerPlacedBlockList = mutableListOf<Location>()
var generatorBlockList = mutableListOf<Location>()
var woolState = hashMapOf<Location, Boolean>()
var slimeBlocks = mutableListOf<Location>()
var woolColor = hashMapOf<UUID, Material>()
var arrows = mutableListOf<Projectile>()


class MainWoolBattle : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        registerEvents()
        registerCommands()
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
        plManager.registerEvents(GameStart(), plugin)
    }
}
