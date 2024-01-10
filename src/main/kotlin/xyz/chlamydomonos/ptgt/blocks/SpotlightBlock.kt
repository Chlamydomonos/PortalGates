package xyz.chlamydomonos.ptgt.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import xyz.chlamydomonos.ptgt.blockentities.SpotlightBlockEntity
import xyz.chlamydomonos.ptgt.blocks.bases.BaseBlock
import xyz.chlamydomonos.ptgt.blocks.utils.BlockTickerUtil
import xyz.chlamydomonos.ptgt.blocks.utils.WaterloggedUtil
import xyz.chlamydomonos.ptgt.loaders.BlockEntityLoader

class SpotlightBlock : BaseBlock(DEFAULT_PROPERTIES), SimpleWaterloggedBlock, EntityBlock {
    companion object {
        val FACING: EnumProperty<Direction> = EnumProperty.create("facing", Direction::class.java)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val face = context.clickedFace
        val baseState = this.defaultBlockState()
        val faceOpposite = face.opposite
        val state = baseState.setValue(FACING, faceOpposite)
        return WaterloggedUtil.getStateForWaterloggedBlock(state, context)
    }

    override fun getLightEmission(state: BlockState?, level: BlockGetter?, pos: BlockPos?): Int {
        return 15
    }

    private val shapes = ArrayList<VoxelShape>()

    init {
        shapes.add(box(4.0, 0.0, 4.0, 12.0, 2.0, 12.0))
        shapes.add(box(4.0, 14.0, 4.0, 12.0, 16.0, 12.0))
        shapes.add(box(4.0, 4.0, 0.0, 12.0, 12.0, 2.0))
        shapes.add(box(4.0, 4.0, 14.0, 12.0, 12.0, 16.0))
        shapes.add(box(0.0, 4.0, 4.0, 2.0, 12.0, 12.0))
        shapes.add(box(14.0, 4.0, 4.0, 16.0, 12.0, 12.0))
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        val facing = state.getValue(FACING).ordinal
        return shapes[facing]
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun getCollisionShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return getShape(state, level, pos, context)
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

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return SpotlightBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockTickerUtil.getTicker(type, BlockEntityLoader.BlockEntities.SPOTLIGHT) {
            super.getTicker(level, state, type)!!
        }
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        isMoving: Boolean
    ) {
        if(!level.isClientSide) {
            SpotlightBlockEntity.tryRemoveAllLight(level, pos, state)
        }
        super.onRemove(state, level, pos, newState, isMoving)
    }
}