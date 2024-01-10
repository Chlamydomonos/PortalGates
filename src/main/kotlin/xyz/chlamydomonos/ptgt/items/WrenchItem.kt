package xyz.chlamydomonos.ptgt.items

import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext
import xyz.chlamydomonos.ptgt.blocks.SpotlightBlock
import xyz.chlamydomonos.ptgt.blocks.bases.RotatableBlock
import xyz.chlamydomonos.ptgt.items.bases.BaseItem

class WrenchItem : BaseItem() {
    override fun useOn(context: UseOnContext): InteractionResult {
        if (context.level.isClientSide) {
            return InteractionResult.PASS
        }

        val player = context.player ?: return InteractionResult.FAIL

        val pos = context.clickedPos
        val level = context.level
        val blockState = level.getBlockState(pos)
        val block = blockState.block

        if (block is RotatableBlock) {
            if (player.isShiftKeyDown) {
                val rotation = blockState.getValue(RotatableBlock.ROTATION)
                val newState = blockState.setValue(RotatableBlock.ROTATION, rotation.rotateClockwise())
                level.setBlockAndUpdate(pos, newState)
            } else {
                val facing = blockState.getValue(RotatableBlock.FACING)
                val facingId = facing.ordinal
                val newFacingId = if (facingId == Direction.values().size - 1) 0 else facingId + 1
                val newFacing = Direction.values()[newFacingId]
                val newState = blockState.setValue(RotatableBlock.FACING, newFacing)
                level.setBlockAndUpdate(pos, newState)
            }
            return InteractionResult.SUCCESS
        }

        if (block is SpotlightBlock) {
            val facing = blockState.getValue(SpotlightBlock.FACING)
            val facingId = facing.ordinal
            val newFacingId = if (facingId == Direction.values().size - 1) 0 else facingId + 1
            val newFacing = Direction.values()[newFacingId]
            val newState = blockState.setValue(SpotlightBlock.FACING, newFacing)
            level.setBlockAndUpdate(pos, newState)
        }

        return InteractionResult.PASS
    }
}