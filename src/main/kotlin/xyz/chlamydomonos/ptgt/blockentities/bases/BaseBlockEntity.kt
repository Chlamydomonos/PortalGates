package xyz.chlamydomonos.ptgt.blockentities.bases

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

open class BaseBlockEntity(type: BlockEntityType<*>, pos: BlockPos, blockState: BlockState) : BlockEntity(
    type, pos, blockState
) {
    open fun tick(level: Level, pos: BlockPos, state: BlockState) {}
}