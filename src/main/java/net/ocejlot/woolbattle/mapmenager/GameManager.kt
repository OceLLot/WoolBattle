package net.ocejlot.woolbattle.mapmenager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.ocejlot.woolbattle.*
import net.ocejlot.woolbattle.data.GameStates
import net.ocejlot.woolbattle.data.PlayerData
import net.ocejlot.woolbattle.data.PlayerPerksData
import net.ocejlot.woolbattle.data.TeamData
import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

val lives = plugin.config.getInt("woolbattle.lives")
val redTeam = TeamData("Red", mutableListOf(), lives, GameManager().redSpawnLoc(), "red")
val blueTeam = TeamData("Blue", mutableListOf(), lives, GameManager().blueSpawnLoc(), "blue")

class GameManager : Listener {

    private fun addPlayerToTeam(player: Player) {
        val minimessage = MiniMessage.miniMessage()

        if (redTeam.members.size > blueTeam.members.size) {
            blueTeam.members.add(player)
            player.playerListName(minimessage.deserialize("<dark_blue>[BLUE] <aqua>${player.name}"))
            playerData[player.uniqueId] = PlayerData(blueTeam, Material.BLUE_WOOL)
            player.sendMessage("Ви приєднались до команди синіх")
        } else {
            redTeam.members.add(player)
            player.playerListName(minimessage.deserialize("<dark_red>[RED] <red>${player.name}"))
            playerData[player.uniqueId] = PlayerData(redTeam, Material.RED_WOOL)
            player.sendMessage("Ви приєднались до команди червоних")
        }
    }

    fun stop(winner: Player) {
        val onlinePlayers = Bukkit.getOnlinePlayers()

        Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<red>Гру завершено! Переміг <${playerData[winner.uniqueId]!!.team.color}>${winner.name}"))

        gameState = GameStates.LOBBY
        onlinePlayers.forEach {
            it.teleport(lobbyLoc())
        }

        playerPlacedBlockList.forEach {
            it.block.type = Material.AIR
        }

        var count = 15
        var taskId = Random.nextInt()
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            if (count <= 0) {
                //Що робити по завершенню таймера
                onlinePlayers.forEach {
                    it.kick(Component.text("Гру завершено!"))
                }

                Bukkit.getScheduler().cancelTask(taskId)
                return@scheduleSyncRepeatingTask
            }
            //Що робити кожен раз, до тих під доки таймер не вимкнеться

            onlinePlayers.forEach {
                it.sendActionBar(MiniMessage.miniMessage().deserialize("<aqua>Вас буде телепортовано у лоббі через <dark_aqua>$count <aqua>сек."))
            }
            count--
        }, 0, 20)
    }

    fun start() {
        gameState = GameStates.GAME

        redTeam.members.forEach { player ->
            player.gameMode = GameMode.SURVIVAL
            Scoreboard().display(player)
            player.inventory.clear()
            player.teleport(redSpawnLoc())
            kitStart(player)
        }

        blueTeam.members.forEach { player ->
            player.gameMode = GameMode.SURVIVAL
            Scoreboard().display(player)
            player.inventory.clear()
            player.teleport(blueSpawnLoc())
            kitStart(player)
        }
    }

    private fun lobbyLoc(): Location {
        val configManager = plugin.config

        val lobbyX = configManager.getDouble("woolbattle.mapmanager.lobbyloc.x")
        val lobbyY = configManager.getDouble("woolbattle.mapmanager.lobbyloc.y")
        val lobbyZ = configManager.getDouble("woolbattle.mapmanager.lobbyloc.z")
        val world = Bukkit.getWorld("world")

        return Location(world, lobbyX, lobbyY, lobbyZ)
    }

    fun redSpawnLoc(): Location {
        val configManager = plugin.config

        val lobbyX = configManager.getDouble("woolbattle.mapmanager.redspawnloc.x")
        val lobbyY = configManager.getDouble("woolbattle.mapmanager.redspawnloc.y")
        val lobbyZ = configManager.getDouble("woolbattle.mapmanager.redspawnloc.z")
        val world = Bukkit.getWorld("world")

        return Location(world, lobbyX, lobbyY, lobbyZ)
    }

    fun blueSpawnLoc(): Location {
        val configManager = plugin.config

        val lobbyX = configManager.getDouble("woolbattle.mapmanager.bluespawnloc.x")
        val lobbyY = configManager.getDouble("woolbattle.mapmanager.bluespawnloc.y")
        val lobbyZ = configManager.getDouble("woolbattle.mapmanager.bluespawnloc.z")
        val world = Bukkit.getWorld("world")

        return Location(world, lobbyX, lobbyY, lobbyZ)
    }

    private fun kitStart(player: Player) {
        val inv = player.inventory
        inv.setItem(0, ItemStorage.shears)  //хотбар 1 слот
        inv.setItem(1, ItemStorage.knockbackBow) //хотбар 2 слот
        inv.setItem(2, ItemStorage.enderpearl) //хотбар 3 слот
        inv.setItem(3, playerPerksData[player.uniqueId]!!.perk1) //хотбар 4 слот
        inv.setItem(4, playerPerksData[player.uniqueId]!!.perk2) //хотбар 5 слот

        inv.setItem(9, ItemStack(Material.ARROW)) //інвентар 1 слот (лівий верхній кут)
    }

    private fun runTimer() {
        var taskId = Random.nextInt()
        var count = 15

        val onlinePlayers = Bukkit.getOnlinePlayers()
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            if (count <= 0) {  //if looped 15 times do:
                Bukkit.getScheduler().cancelTask(taskId)  //cancel loop
                start()
                onlinePlayers.forEach {
                    it.playSound(it.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F)
                }
                return@scheduleSyncRepeatingTask
            }

            onlinePlayers.forEach {
                it.sendActionBar(MiniMessage.miniMessage().deserialize("<aqua>Гра почнеться через <dark_aqua>$count<aqua> сек."))
            }
            count--  //-1 from timers
        }, 20, 20)  //20 ticks delay
    }


    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val onlinePlayers = Bukkit.getOnlinePlayers().size
        val player = event.player
        val inventory = player.inventory

        addPlayerToTeam(player)
        player.teleport(lobbyLoc())
        player.gameMode = GameMode.ADVENTURE
        playerPerksData[player.uniqueId] = PlayerPerksData()
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 9999999, 0, false, false, false))
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F)

        inventory.clear()
        inventory.setItem(0, ItemStorage.perkMenuItem)

        if (onlinePlayers < 3) {
            event.joinMessage(MiniMessage.miniMessage().deserialize("<aqua>[$onlinePlayers/2] <white>${player.name} приєднався до гри!"))
            if (onlinePlayers == 2) {
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    runTimer()
                }, 20 * 5)
            }
        } else {
            event.joinMessage(Component.text(""))
            player.kick(Component.text("Занадто багато гравців, ви не можете під'єднатись!"))
        }
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        player.removePotionEffect(PotionEffectType.SATURATION)
        playerData[player.uniqueId]!!.team.lives = 6
        playerData.remove(player.uniqueId)
    }
}
