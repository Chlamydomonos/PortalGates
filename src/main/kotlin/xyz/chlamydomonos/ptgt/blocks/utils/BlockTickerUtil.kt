package xyz.chlamydomonos.ptgt.blocks.utils

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import xyz.chlamydomonos.ptgt.blockentities.bases.BaseBlockEntity

object BlockTickerUtil {
    class DefaultBlockTicker<T : BaseBlockEntity> : BlockEntityTicker<T> {
        override fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: T) {
            return blockEntity.tick(level, pos, state)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BlockEntity, U : BaseBlockEntity> getTicker(
        type: BlockEntityType<T>,
        requiredType: BlockEntityType<U>,
        superGetTicker: () -> BlockEntityTicker<T>
    ): BlockEntityTicker<T> {
        return if(type == requiredType) {
            DefaultBlockTicker<U>() as BlockEntityTicker<T>
        } else {
            superGetTicker()
        }
    }
}