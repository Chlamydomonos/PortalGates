package xyz.chlamydomonos.ptgt.blocks.bases

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Material

open class BaseBlock(properties: Properties) : Block(properties) {
    companion object {
        val DEFAULT_PROPERTIES: Properties = Properties.of(Material.METAL)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F)
    }
}