package xyz.chlamydomonos.ptgt.items.bases

import net.minecraft.world.item.BlockItem
import xyz.chlamydomonos.ptgt.blocks.bases.BaseBlock
import xyz.chlamydomonos.ptgt.loaders.CreativeTabLoader

class BaseBlockItem <T : BaseBlock>(block: T) : BlockItem(block, Properties().tab(CreativeTabLoader.TAB_PTGT)) {
    @Suppress("UNCHECKED_CAST")
    override fun getBlock(): T {
        return super.getBlock() as T
    }
}