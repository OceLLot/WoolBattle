package net.ocejlot.jakashuinya.jakashuinya.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import net.ocejlot.jakashuinya.jakashuinya.wbDebugger
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WoolBattleCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) return false
        if(!sender.hasPermission("woolbattle.debug")) return false

        //Встановлює значення дебагмоду на протилежне
        val uuid = sender.uniqueId
        val mode = wbDebugger.putIfAbsent(uuid, false)
        if (mode != null) {
            wbDebugger[uuid] = !mode
            sender.sendMessage("Ви встановили дебаг мод на ${!mode}")
        }
        return true
    }
}