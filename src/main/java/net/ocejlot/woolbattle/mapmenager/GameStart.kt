package net.ocejlot.woolbattle.mapmenager

import net.kyori.adventure.text.Component
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

class GameStart: Listener{

    companion object {
        val redTeam = TeamData("Red", mutableListOf())
        val blueTeam = TeamData("Blue", mutableListOf())
    }

    private fun addPlayerToTeam(player: Player) {
        val condition = redTeam.members.size > blueTeam.members.size
        Bukkit.broadcast(Component.text(condition.toString()))
        Bukkit.broadcast(Component.text(redTeam.members.size))
        Bukkit.broadcast(Component.text(blueTeam.members.size))

        if (condition) {
            blueTeam.members.add(player)
            player.setPlayerListName("§3[BLUE] §b${player.name}")
            woolColor[player.uniqueId] = Material.BLUE_WOOL
            player.sendMessage("Ви приєднались до команди синіх")
        } else {
            redTeam.members.add(player)
            player.setPlayerListName("§4[RED] §c${player.name}")
            woolColor[player.uniqueId] = Material.RED_WOOL
            player.sendMessage("Ви приєднались до команди червоних")
        }
    }

    fun start(){
        val world = Bukkit.getWorld("world")
        val redTeamLoc = Location(world, 10.5, 100.0, 0.5)
        val blueTeamLoc = Location(world, -10.5, 100.0, 0.5)

        redTeam.members.forEach{player ->
            player.teleport(redTeamLoc)
            kitStart(player)
        }

        blueTeam.members.forEach{player ->
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
        addPlayerToTeam(event.player)
    }
}
