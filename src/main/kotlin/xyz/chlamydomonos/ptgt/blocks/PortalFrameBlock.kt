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
import xyz.chlamydomonos.ptgt.blocks.bases.Rotatable2PortBlock
import xyz.chlamydomonos.ptgt.blocks.utils.WaterloggedUtil

class PortalFrameBlock : Rotatable2PortBlock(DEFAULT_PROPERTIES.noOcclusion()), SimpleWaterloggedBlock {
    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val superState = super.getStateForPlacement(context)
        return WaterloggedUtil.getStateForWaterloggedBlock(superState, context)
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun updateShape(
        state: BlockState,
        facing: Direction,
        facingState: BlockState,
        level: LevelAccessor,
        currentPos: BlockPos,
        facingPos: BlockPos
    ): BlockState {
        WaterloggedUtil.updateShape(state, level, currentPos)
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos)
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun getFluidState(state: BlockState): FluidState {
        return WaterloggedUtil.getFluidState(state) { super.getFluidState(state) }
    }
}