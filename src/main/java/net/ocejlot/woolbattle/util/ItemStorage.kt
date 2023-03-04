package net.ocejlot.woolbattle.util

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object ItemStorage {
    val slimePlatformItem = ItemStack(Material.SLIME_BALL).apply {
        itemMeta = itemMeta.also { its ->
            its.displayName(MiniMessage.miniMessage().deserialize("<italic:false><green>Слайм-платформа"))
        }
    }

    val woolRoundPlatformItem = ItemStack(Material.BLAZE_ROD).apply {
        itemMeta = itemMeta.also { its ->
            its.displayName(MiniMessage.miniMessage().deserialize("<italic:false><red>Вовнова-платформа"))
        }
    }
    val woolCapsuleItem = ItemStack(Material.WHITE_WOOL).apply {
        itemMeta = itemMeta.also { its ->
            its.displayName(MiniMessage.miniMessage().deserialize("<italic:false><blue>Капсула з вовни"))
        }
    }
    val shears = ItemStack(Material.SHEARS).apply {
        itemMeta = itemMeta.also { its ->
            its.displayName(MiniMessage.miniMessage().deserialize("<italic:false><yellow>Чарівні ножиці"))
            its.isUnbreakable = true
            its.addEnchant(Enchantment.DIG_SPEED, 10, true)
            its.addEnchant(Enchantment.KNOCKBACK, 3, true)
        }
    }

    val knockbackBow = ItemStack(Material.BOW).apply {
        itemMeta = itemMeta.also { its ->
            its.displayName(MiniMessage.miniMessage().deserialize("<italic:false><blue>Чарівний лук"))
            its.isUnbreakable = true
            its.addEnchant(Enchantment.ARROW_KNOCKBACK, 5, true)
            its.addEnchant(Enchantment.KNOCKBACK, 3, true)
            its.addEnchant(Enchantment.ARROW_INFINITE, 1, true)
        }
    }
}
