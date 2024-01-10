package xyz.chlamydomonos.ptgt.loaders

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack


object CreativeTabLoader {
    val TAB_PTGT = object : CreativeModeTab("ptgt") {
        override fun makeIcon(): ItemStack {
            return ItemStack(BlockLoader.Blocks.PORTAL_FRAME.item)
        }
    }
}