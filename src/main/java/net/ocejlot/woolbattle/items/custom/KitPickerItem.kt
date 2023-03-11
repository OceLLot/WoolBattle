package net.ocejlot.woolbattle.items.custom

import net.kyori.adventure.text.Component
import net.ocejlot.woolbattle.playerPerksData
import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

class KitPickerItem: Listener {
    private val menuName = "Вибрати перк"
    private val pickSlotMenuName = "Вибрати слот"

    @EventHandler
    fun onRightClick(event: PlayerInteractEvent){
        val player = event.player
        val item = event.item ?: return

        if(item != ItemStorage.perkMenuItem)return
        if(event.hand != EquipmentSlot.HAND)return
        if(event.action == Action.LEFT_CLICK_BLOCK || event.action == Action.LEFT_CLICK_AIR)return

        player.openInventory(pickSlotMenu(player))
    }

    private fun pickSlotMenu(player: Player) = Bukkit.createInventory(player, InventoryType.CHEST, pickSlotMenuName).also {

        it.setItem(0, ItemStack(Material.CHEST).apply {
            itemMeta = itemMeta.also {its ->
                its.displayName(Component.text("Перший перк"))
                its.lore = mutableListOf(" ")
            }
        })

        it.setItem(1, ItemStack(Material.CHEST).apply {
            itemMeta = itemMeta.also {its ->
                its.displayName(Component.text("Другий перк"))
                its.lore = mutableListOf(" ")
            }
        })

        it.setItem(2, ItemStack(Material.ENDER_CHEST).apply {
            itemMeta = itemMeta.also {its ->
                its.displayName(Component.text("Пасивна навичка"))
                its.lore = mutableListOf(" ")
            }
        })
    }

    private fun menu(player: Player) = Bukkit.createInventory(player, InventoryType.CHEST, menuName).also {

        it.setItem(0, ItemStorage.woolCapsuleItem.apply {
            itemMeta = itemMeta.also {its ->
                its.lore = mutableListOf(" ", "Капсула із вовни, коштує: 16")
            }
        })

        it.setItem(1, ItemStorage.woolRoundPlatformItem.apply {
            itemMeta = itemMeta.also {its ->
                its.lore = mutableListOf(" ", "Кругла платформа із вовни, коштує: 16")
            }
        })

        it.setItem(2, ItemStorage.jumpPlatformItem.apply {
            itemMeta = itemMeta.also {its ->
                its.lore = mutableListOf(" ", "Платформа що підкидає вверх, коштує: 16")
            }
        })

        it.setItem(3, ItemStorage.rideableCrossbow.apply {
            itemMeta = itemMeta.also {its ->
                its.lore = mutableListOf(" ", "Арбалет на якому можна літати, коштує: 16")
            }
        })

        it.setItem(4, ItemStorage.swapSnowball.apply {
            itemMeta = itemMeta.also {its ->
                its.lore = mutableListOf(" ", "Сніжка що міняє вас місцями з опонентом, коштує: 16")
            }
        })
    }

    //pickSlotMenu
    @EventHandler
    fun onPickSlotMenuClick(event: InventoryClickEvent){
        val player = event.whoClicked as Player

        if(event.whoClicked !is Player)return
        if(event.view.title != pickSlotMenuName)return

        event.isCancelled = true

        when(event.slot){
            0, 1 -> {
                playerPerksData[player.uniqueId]!!.pickedSlot = event.slot
                player.openInventory(menu(player))
            }
            2 -> {
                player.sendMessage("3")
            }
            else -> {}
        }
    }


    //menu
    @EventHandler
    fun onMenuClick(event: InventoryClickEvent){
        val player = event.whoClicked as Player

        if(event.whoClicked !is Player)return
        if(event.view.title != menuName)return

        event.isCancelled = true

        when(event.slot){
            0, 1, 2, 3, 4 -> {
                player.inventory.close()
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F)

                val pickedSlot = playerPerksData[player.uniqueId]!!.pickedSlot
                if (pickedSlot == 0) {
                    playerPerksData[player.uniqueId]!!.perk1 = event.currentItem!!
                } else if (pickedSlot == 1) {
                    playerPerksData[player.uniqueId]!!.perk2 = event.currentItem!!
                }
            }
            else -> {}
        }
    }
}
