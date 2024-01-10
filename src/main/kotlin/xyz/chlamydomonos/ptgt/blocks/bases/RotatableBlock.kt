package xyz.chlamydomonos.ptgt.blocks.bases

import net.minecraft.core.Direction
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.EnumProperty
import java.util.*

open class RotatableBlock(properties: Properties) : BaseBlock(properties) {
    enum class Rotation : StringRepresentable {
        NONE,
        CLOCKWISE_90,
        CLOCKWISE_180,
        CLOCKWISE_270;

        override fun getSerializedName(): String {
            return name.lowercase(Locale.getDefault())
        }

        fun rotateClockwise(): Rotation {
            return when (this) {
                NONE -> CLOCKWISE_90
                CLOCKWISE_90 -> CLOCKWISE_180
                CLOCKWISE_180 -> CLOCKWISE_270
                CLOCKWISE_270 -> NONE
            }
        }

        @Suppress("unused")
        fun rotateCounterClockwise(): Rotation {
            return when (this) {
                NONE -> CLOCKWISE_270
                CLOCKWISE_90 -> NONE
                CLOCKWISE_180 -> CLOCKWISE_90
                CLOCKWISE_270 -> CLOCKWISE_180
            }
        }
    }

    companion object {
        val FACING: EnumProperty<Direction> = EnumProperty.create("facing", Direction::class.java)
        val ROTATION: EnumProperty<Rotation> = EnumProperty.create("rotation", Rotation::class.java)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, ROTATION)
    }
}