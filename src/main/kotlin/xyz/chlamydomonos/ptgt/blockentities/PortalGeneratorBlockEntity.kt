package xyz.chlamydomonos.ptgt.blockentities

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import xyz.chlamydomonos.ptgt.blockentities.bases.BaseBlockEntity
import xyz.chlamydomonos.ptgt.loaders.BlockEntityLoader

class PortalGeneratorBlockEntity(pos: BlockPos, blockState: BlockState) : BaseBlockEntity(
    BlockEntityLoader.BlockEntities.PORTAL_GENERATOR, pos, blockState
) {

}