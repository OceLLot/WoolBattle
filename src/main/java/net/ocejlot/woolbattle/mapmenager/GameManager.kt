package net.ocejlot.woolbattle.mapmenager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.ocejlot.woolbattle.data.PlayerData
import net.ocejlot.woolbattle.data.PlayerPerksData
import net.ocejlot.woolbattle.data.TeamData
import net.ocejlot.woolbattle.playerData
import net.ocejlot.woolbattle.playerPerksData
import net.ocejlot.woolbattle.plugin
import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

val redTeam = TeamData("Red", mutableListOf(), 12)
val blueTeam = TeamData("Blue", mutableListOf(), 12)

class GameStart: Listener{

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

    fun start(){
        val world = Bukkit.getWorld("world")
        val redTeamLoc = Location(world, 10.5, 100.0, 0.5)
        val blueTeamLoc = Location(world, -10.5, 100.0, 0.5)

        redTeam.members.forEach{player ->
            Scoreboard().display(player)
            player.inventory.clear()
            player.teleport(redTeamLoc)
            kitStart(player)
        }

        blueTeam.members.forEach{player ->
            Scoreboard().display(player)
            player.inventory.clear()
            player.teleport(blueTeamLoc)
            kitStart(player)
        }
    }

    private fun kitStart(player: Player){
        val inv = player.inventory
        inv.setItem(0, ItemStorage.shears)  //хотбар 1 слот
        inv.setItem(1, ItemStorage.knockbackBow) //хотбар 2 слот
        inv.setItem(2, ItemStorage.enderpearl) //хотбар 3 слот
        inv.setItem(3, playerPerksData[player.uniqueId]!!.perk1) //хотбар 4 слот
        inv.setItem(4, playerPerksData[player.uniqueId]!!.perk2) //хотбар 5 слот

        inv.setItem(9, ItemStack(Material.ARROW)) //інвентар 1 слот (лівий верхній кут)
    }


    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val onlinePlayers = Bukkit.getOnlinePlayers().size
        val player = event.player
        val inventory = player.inventory

        addPlayerToTeam(player)
        playerPerksData[player.uniqueId] = PlayerPerksData()
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 9999999, 0, false, false, false))
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F)

        inventory.clear()
        inventory.setItem(0, ItemStorage.perkMenuItem)

        if(onlinePlayers < 3) {
            event.joinMessage(MiniMessage.miniMessage().deserialize("<aqua>[$onlinePlayers/2] <white>${player.name} приєднався до гри!"))
            if(onlinePlayers == 2){
                runTimer()
            }
        }else{
            event.joinMessage(Component.text(""))
            player.kick(Component.text("Занадто багато гравців, ви не можете під'єднатись!"))
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent){
        val player = event.player
        player.removePotionEffect(PotionEffectType.SATURATION)
        redTeam.members.remove(player)
        blueTeam.members.remove(player)
        playerData.remove(player.uniqueId)
    }

    private fun runTimer(){
        var taskId = Random.nextInt()
        var count = 15

        val onlinePlayers = Bukkit.getOnlinePlayers()
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            if(count <= 0) {  //if looped 15 times do:
                Bukkit.getScheduler().cancelTask(taskId)  //cancel loop
                start()
                onlinePlayers.forEach{
                    it.playSound(it.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F)
                }
                return@scheduleSyncRepeatingTask
            }

            onlinePlayers.forEach{
                it.sendActionBar(MiniMessage.miniMessage().deserialize("Гра почнеться через $count сек."))
            }
            count--  //-1 from timers
        },20,20)  //20 ticks delay
    }
}
