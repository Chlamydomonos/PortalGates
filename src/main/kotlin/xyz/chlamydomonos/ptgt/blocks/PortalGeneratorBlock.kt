package xyz.chlamydomonos.ptgt.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import xyz.chlamydomonos.ptgt.blockentities.PortalGeneratorBlockEntity
import xyz.chlamydomonos.ptgt.blocks.bases.Rotatable2PortBlock
import xyz.chlamydomonos.ptgt.blocks.utils.BlockTickerUtil
import xyz.chlamydomonos.ptgt.loaders.BlockEntityLoader

class PortalGeneratorBlock : Rotatable2PortBlock(DEFAULT_PROPERTIES), EntityBlock {

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return PortalGeneratorBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockTickerUtil.getTicker(type, BlockEntityLoader.BlockEntities.PORTAL_GENERATOR) {
            super.getTicker(level, state, type)!!
        }
    }
}