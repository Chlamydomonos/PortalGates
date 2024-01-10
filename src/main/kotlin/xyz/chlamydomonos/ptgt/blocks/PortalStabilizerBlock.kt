package xyz.chlamydomonos.ptgt.blocks

import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.state.BlockState
import xyz.chlamydomonos.ptgt.blocks.bases.RotatableBlock

class PortalStabilizerBlock : RotatableBlock(DEFAULT_PROPERTIES) {
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val face = context.clickedFace
        val baseState = this.defaultBlockState()
        val faceOpposite = face.opposite
        // TODO("根据视角，以类似楼梯的方式旋转方块")
        return baseState.setValue(FACING, faceOpposite)
    }
}