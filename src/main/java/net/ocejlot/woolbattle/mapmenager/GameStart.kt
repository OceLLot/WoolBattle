package net.ocejlot.woolbattle.mapmenager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.ocejlot.woolbattle.data.TeamData
import net.ocejlot.woolbattle.util.ItemStorage
import net.ocejlot.woolbattle.woolColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scoreboard.DisplaySlot

val redTeam = TeamData("Red", mutableListOf(), 12)
val blueTeam = TeamData("Blue", mutableListOf(), 12)

class GameStart: Listener{

    private fun addPlayerToTeam(player: Player) {
        val minimessage = MiniMessage.miniMessage()

        if (redTeam.members.size > blueTeam.members.size) {
            blueTeam.members.add(player)
            player.playerListName(minimessage.deserialize("<dark_blue>[BLUE] <aqua>${player.name}"))
            woolColor[player.uniqueId] = Material.BLUE_WOOL
            player.sendMessage("Ви приєднались до команди синіх")
        } else {
            redTeam.members.add(player)
            player.playerListName(minimessage.deserialize("<dark_red>[RED] <red>${player.name}"))
            woolColor[player.uniqueId] = Material.RED_WOOL
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

        inv.setItem(9, ItemStack(Material.ARROW)) //інвентар 1 слот (лівий верхній кут)
    }


    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val onlinePlayers = Bukkit.getOnlinePlayers().size
        val player = event.player
        addPlayerToTeam(player)
        if(onlinePlayers < 3) {
            event.joinMessage(MiniMessage.miniMessage().deserialize("<aqua>[$onlinePlayers/2] <white>${player.name} приєднався до гри!"))
        }else{
            event.joinMessage(Component.text(""))
            player.kick(Component.text("Занадто багато гравців, ви не можете під'єднатись!"))
        }
    }
}
