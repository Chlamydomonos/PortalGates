package xyz.chlamydomonos.ptgt.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import xyz.chlamydomonos.ptgt.blocks.bases.BaseBlock

class SpotlightBlock : BaseBlock(DEFAULT_PROPERTIES) {
    companion object {
        val FACING: EnumProperty<Direction> = EnumProperty.create("facing", Direction::class.java)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val face = context.clickedFace
        val baseState = this.defaultBlockState()
        val faceOpposite = face.opposite
        return baseState.setValue(FACING, faceOpposite)
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
}