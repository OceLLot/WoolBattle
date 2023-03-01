package net.ocejlot.jakashuinya.jakashuinya.util

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object ItemStorage {
    val slimePlatformItem = ItemStack(Material.SLIME_BALL).apply {
        itemMeta = itemMeta.also { its ->
            its.displayName(MiniMessage.miniMessage().deserialize("<reset><green>Слайм-платформа"))
        }
    }

    val shears = ItemStack(Material.SHEARS).apply {
        itemMeta = itemMeta.also { its ->
            its.displayName(MiniMessage.miniMessage().deserialize("<reset><yellow>Чарівні ножиці"))
            its.isUnbreakable = true
            its.addEnchant(Enchantment.DIG_SPEED, 10, true)
        }
    }
}