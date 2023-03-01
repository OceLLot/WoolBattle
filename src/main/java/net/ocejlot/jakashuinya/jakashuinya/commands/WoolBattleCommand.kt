package net.ocejlot.jakashuinya.jakashuinya.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import net.ocejlot.jakashuinya.jakashuinya.playerPlacedBlockList
import net.ocejlot.jakashuinya.jakashuinya.util.ItemStorage
import net.ocejlot.jakashuinya.jakashuinya.wbDebugger
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class WoolBattleCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (!sender.hasPermission("woolbattle.debug")) return false
        if (args.isEmpty()) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Комманда введена неправильно!"))
            return false
        }


        //Якщо чувак ввів /woolbattle debug
        if (args[0] == "debug") {
            //Встановлює значення дебагмоду на протилежне
            val uuid = sender.uniqueId
            val mode = wbDebugger.putIfAbsent(uuid, false)
            if (mode == null) {
                sender.sendMessage("Ви встановили дебаг мод на false")
            } else {
                wbDebugger[uuid] = !mode
                sender.sendMessage("Ви встановили дебаг мод на ${!mode}")
            }


        //Якщо чувак ввів /woolbattle item
        }else if (args[0] == "item"){
            if(args.size < 2){
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Не вистачає аргумента!"))
                return false
            }
            val inventory = sender.inventory

            //Якщо чувак ввів /woolbattle item shears
            if(args[1] == "shears"){
                inventory.addItem(ItemStorage.shears)
            }

            //Якщо чувак ввів /woolbattle item slime
            if(args[1] == "slime"){
                inventory.addItem(ItemStorage.slimePlatformItem)
            }

        }
    return false}

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if(args.size == 1){
            return mutableListOf("debug", "item")
        }

        if(args.size == 2){
            return mutableListOf("shears", "slime")
        }

    return mutableListOf()
    }
}
