package net.ocejlot.jakashuinya.jakashuinya.util

import org.bukkit.Material

class IsWool(
        private val block: Material
) {
    fun get() : Boolean{
        if(block.toString().endsWith("WOOL")) return true; return false
    }
}