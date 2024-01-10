package xyz.chlamydomonos.ptgt.loaders.utils

import xyz.chlamydomonos.ptgt.blocks.bases.BaseBlock
import xyz.chlamydomonos.ptgt.items.bases.BaseBlockItem

data class BlockAndItsItem<T : BaseBlock>(
    val name: String,
    val block: T,
    val item: BaseBlockItem<T>
)