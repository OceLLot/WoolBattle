package net.ocejlot.woolbattle.features

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class WoolActions(
        player: Player
){
    private val inventory = player.inventory

    //Відняти кількість вовни
    fun reduceAmount(amount: Int){
        inventory.removeItem(ItemStack(Material.RED_WOOL, amount))
    }

    //Додати кількість вовни
    fun addAmount(amount: Int){
        inventory.addItem(ItemStack(Material.RED_WOOL, amount))
    }
}
