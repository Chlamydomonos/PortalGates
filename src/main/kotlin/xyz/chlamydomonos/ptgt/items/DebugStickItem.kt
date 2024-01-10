package xyz.chlamydomonos.ptgt.items

import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext
import xyz.chlamydomonos.ptgt.items.bases.BaseItem
import xyz.chlamydomonos.ptgt.blocks.bases.RotatableBlock

class DebugStickItem : BaseItem() {
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
            val facing = blockState.getValue(RotatableBlock.FACING)
            val rotation = blockState.getValue(RotatableBlock.ROTATION)
            val message = "facing: $facing, rotation: $rotation"
            player.sendSystemMessage(Component.literal(message))
            return InteractionResult.SUCCESS
        }

        return InteractionResult.PASS
    }
}