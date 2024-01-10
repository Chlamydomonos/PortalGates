package xyz.chlamydomonos.ptgt.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids

class WaterloggedBrightAirBlock : BrightAirBlock(), SimpleWaterloggedBlock {
    init {
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, true))
    }

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(pBuilder)
        pBuilder.add(BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(pContext: BlockPlaceContext): BlockState? {
        val fluidState = pContext.level.getFluidState(pContext.clickedPos)
        return super.getStateForPlacement(pContext)!!
            .setValue(BlockStateProperties.WATERLOGGED, fluidState.type === Fluids.WATER && fluidState.isSource)
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun updateShape(
        pState: BlockState,
        pFacing: Direction,
        pFacingState: BlockState,
        pLevel: LevelAccessor,
        pCurrentPos: BlockPos,
        pFacingPos: BlockPos
    ): BlockState {
        if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel))
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos)
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun getFluidState(pState: BlockState): FluidState {
        return if (pState.getValue(BlockStateProperties.WATERLOGGED)) Fluids.WATER.getSource(false) else super.getFluidState(
            pState
        )
    }
}