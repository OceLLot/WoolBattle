package net.ocejlot.woolbattle.mapmenager

import net.kyori.adventure.text.minimessage.MiniMessage
import net.ocejlot.woolbattle.playerData
import net.ocejlot.woolbattle.plugin
import net.ocejlot.woolbattle.wbDebugger
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener

val minY = plugin.config.getDouble("woolbattle.mapmanager.miny")

class ResetOnDeath : Listener {

    fun playerYCoordinate() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, Runnable {
            if (Bukkit.getOnlinePlayers().size < 2) return@Runnable

            Bukkit.getOnlinePlayers().forEach { player ->
                if (wbDebugger[player.uniqueId] == true) return@forEach

                if (player.location.y in minY..minY + 15) {
                    val lowIndicator = (player.location.y - minY).toInt()
                    player.sendActionBar(MiniMessage.miniMessage().deserialize("<gray>До найнижчої висоти <red>$lowIndicator<gray> блоків"))
                }

                if (player.location.y > minY) return@forEach

                val playerTeam = playerData[player.uniqueId]!!.team
                player.teleport(playerData[player.uniqueId]!!.team.loc)

                val lastPunchedPlayer = playerData[player.uniqueId]!!.lastPunchedPlayer

                if (lastPunchedPlayer != null) {
                    Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<${playerData[player.uniqueId]!!.team.color}>${player.name} <gray>був вбитий</gray> <${playerData[lastPunchedPlayer.uniqueId]!!.team.color}>${lastPunchedPlayer.name}"))
                    playerData[player.uniqueId]!!.team.lives--
                    playerData[player.uniqueId]!!.lastPunchedPlayer = null
                }

                if (playerTeam.lives >= 0) {
                    //Оновити скорборд
                    Scoreboard().scoreboard()

                    //Показати оновлений скорборд всім
                    Bukkit.getOnlinePlayers().forEach {
                        Scoreboard().display(it)
                    }
                } else {
                    val onlinePlayers = Bukkit.getOnlinePlayers()
                    if (onlinePlayers.size < 2) return@forEach

                    val playerList = mutableListOf<Player>()
                    onlinePlayers.forEach {
                        playerList.add(it)
                        //Очистити скорборд
                        it.scoreboard = Bukkit.getScoreboardManager().newScoreboard
                        it.inventory.clear()
                    }
                    playerList.remove(player)

                    val winner = playerList[0]

                    GameManager().stop(winner)
                }
            }
        }, 0, 10)
    }
}
