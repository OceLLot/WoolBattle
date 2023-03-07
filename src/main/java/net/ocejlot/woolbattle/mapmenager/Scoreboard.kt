package net.ocejlot.woolbattle.mapmenager

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard

class Scoreboard {
    fun scoreboard(): Scoreboard{
        val manager = Bukkit.getScoreboardManager()
        val scoreboard = manager.newScoreboard
        val objective = scoreboard.registerNewObjective("WoolBattle", "")
        objective.displaySlot = DisplaySlot.SIDEBAR
        objective.displayName(MiniMessage.miniMessage().deserialize("  <gold>Craftoriya <yellow>>>> <red>Wool<blue>Battle "))

        val redLives = objective.getScore("> §c♥ §f${redTeam.lives} < §cRED")
        val blueLives = objective.getScore("> §c♥ §f${blueTeam.lives} < §bBLUE")
        val craftoriya = objective.getScore("§6Craftoriya.net")

        //----------
        objective.getScore("  ").score = 5
        redLives.score = 4
        blueLives.score = 3
        objective.getScore(" ").score = 2
        craftoriya.score = 1
        //-----------

        return scoreboard
    }

    fun display(player: Player){
        player.scoreboard = scoreboard()
    }
}