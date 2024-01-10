package xyz.chlamydomonos.ptgt.loaders.utils

import xyz.chlamydomonos.ptgt.items.bases.BaseItem

data class ItemWithHandheldInfo<T : BaseItem>(
    val name: String,
    val item: T,
    val isHandheld: Boolean
)