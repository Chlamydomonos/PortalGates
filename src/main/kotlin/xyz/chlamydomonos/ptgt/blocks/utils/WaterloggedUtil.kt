package xyz.chlamydomonos.ptgt.blocks.utils

import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.CampfireBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids

object WaterloggedUtil {
    fun getStateForWaterloggedBlock(state: BlockState, context: BlockPlaceContext): BlockState {
        val level = context.level
        val pos = context.clickedPos
        val hasWater = level.getFluidState(pos).type == Fluids.WATER
        return state.setValue(BlockStateProperties.WATERLOGGED, hasWater)
    }

    fun updateShape(state: BlockState, level: LevelAccessor, currentPos: BlockPos?) {
        if (state.getValue(CampfireBlock.WATERLOGGED)) {
            level.scheduleTick(currentPos!!, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        }
    }

    fun getFluidState(pState: BlockState, superResult: () -> FluidState): FluidState {
        return if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
            Fluids.WATER.getSource(false)
        } else {
            superResult()
        }
    }
}