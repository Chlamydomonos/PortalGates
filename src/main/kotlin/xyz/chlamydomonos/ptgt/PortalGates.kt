package xyz.chlamydomonos.ptgt

import net.minecraftforge.fml.common.Mod
import xyz.chlamydomonos.ptgt.loaders.BlockEntityLoader
import xyz.chlamydomonos.ptgt.loaders.BlockLoader
import xyz.chlamydomonos.ptgt.loaders.ItemLoader

@Mod(PortalGates.MODID)
object PortalGates {
    const val MODID = "ptgt"

    init {
        BlockLoader.initAll()
        ItemLoader.initAll()
        BlockEntityLoader.initAll()
    }
}