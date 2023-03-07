package net.ocejlot.woolbattle.features

import net.ocejlot.woolbattle.woolColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class WoolActions(
        player: Player
){
    private val inventory = player.inventory
    private val uuid = player.uniqueId

    //Відняти кількість вовни
    fun reduceAmount(amount: Int){
        val wool = woolColor[uuid]!!
        inventory.removeItem(ItemStack(wool, amount))
    }

    //Додати кількість вовни
    fun addAmount(amount: Int){
        val wool = woolColor[uuid]!!
        inventory.addItem(ItemStack(wool, amount))
    }
}
