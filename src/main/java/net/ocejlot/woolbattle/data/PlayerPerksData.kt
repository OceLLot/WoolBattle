package net.ocejlot.woolbattle.data

import net.ocejlot.woolbattle.util.ItemStorage
import org.bukkit.inventory.ItemStack

data class PlayerPerksData(
        var perk1: ItemStack = ItemStorage.woolCapsuleItem,
        var perk2: ItemStack = ItemStorage.woolRoundPlatformItem
)
