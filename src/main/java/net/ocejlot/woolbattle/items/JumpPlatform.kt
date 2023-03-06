package net.ocejlot.woolbattle.items
import net.ocejlot.woolbattle.feachers.ItemFeatures
import net.ocejlot.woolbattle.feachers.WoolActions
import net.ocejlot.woolbattle.util.ItemAmount
import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class JumpPlatform : Listener{

    @EventHandler
    fun onRightClick(event: PlayerInteractEvent){
        val player = event.player
        val item = event.item
        val amount = 16

        if(event.hand != EquipmentSlot.HAND)return
        if(item != ItemStorage.jumpPlatformItem)return
        if(event.action == Action.LEFT_CLICK_AIR || event.action == Action.LEFT_CLICK_BLOCK)return
        if(ItemAmount.getPlayerItemCount(player, Material.RED_WOOL) < amount)return

        val location = player.location.add(0.0, -6.0, 0.0)
        ItemFeatures().spawnCrossPlatform(location, Material.SLIME_BLOCK)
        WoolActions(player).reduceAmount(16)
    }
}
