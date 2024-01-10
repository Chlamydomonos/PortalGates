package xyz.chlamydomonos.ptgt.blocks.bases

import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.state.BlockState

open class Rotatable2PortBlock(properties: Properties) : RotatableBlock(properties) {
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val face = context.clickedFace
        val baseState = this.defaultBlockState()
        val faceOpposite = face.opposite
        // TODO("识别周围的方块，自动把导线方向旋转到合适位置")
        return baseState.setValue(FACING, faceOpposite).setValue(ROTATION, Rotation.NONE)
    }
}