package xyz.chlamydomonos.ptgt.blockentities

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import thedarkcolour.kotlinforforge.forge.vectorutil.times
import xyz.chlamydomonos.ptgt.blockentities.bases.BaseBlockEntity
import xyz.chlamydomonos.ptgt.blocks.SpotlightBlock
import xyz.chlamydomonos.ptgt.loaders.BlockEntityLoader
import xyz.chlamydomonos.ptgt.loaders.BlockLoader

class SpotlightBlockEntity(pos: BlockPos, blockState: BlockState) : BaseBlockEntity(
    BlockEntityLoader.BlockEntities.SPOTLIGHT, pos, blockState
) {
    companion object {
        private const val MAX_COUNT_DOWN = 20
        private const val RANGE = 16

        private fun isAir(blockState: BlockState): Boolean {
            val block = blockState.block
            return block == Blocks.AIR
                    || block == Blocks.CAVE_AIR
                    || block == BlockLoader.Blocks.BRIGHT_AIR
        }

        private fun isBrightAir(blockState: BlockState): Boolean {
            val block = blockState.block
            return block == BlockLoader.Blocks.BRIGHT_AIR
        }

        private fun tryAddLight(level: Level, pos: BlockPos) {
            val state = level.getBlockState(pos)
            val newState = BlockLoader.Blocks.BRIGHT_AIR.defaultBlockState()
            if (!isBrightAir(state)) {
                level.setBlockAndUpdate(pos, newState)
            }
        }

        private fun tryRemoveLight(level: Level, pos: BlockPos) {
            val state = level.getBlockState(pos)
            val block = state.block
            if (block != BlockLoader.Blocks.BRIGHT_AIR) {
                return
            }

            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
        }

        fun tryRemoveAllLight(level: Level, pos: BlockPos, state: BlockState) {
            if (state.block != BlockLoader.Blocks.SPOTLIGHT.block) {
                return
            }

            val facing = state.getValue(SpotlightBlock.FACING).opposite

            var currentPos = pos
            for (i in 1..RANGE) {
                currentPos = currentPos.relative(facing)
                tryRemoveLight(level, currentPos)
            }
        }
    }

    private var countDown = 0

    private var brightAirBlocks = HashSet<Int>()

    override fun tick(level: Level, pos: BlockPos, state: BlockState) {
        if(level.isClientSide) {
            return
        }

        if(state.block != BlockLoader.Blocks.SPOTLIGHT.block) {
            return
        }

        if (countDown == 0) {
            tickSecond(level, pos, state)
            countDown = MAX_COUNT_DOWN
            return
        }
        countDown--
    }

    private fun tickSecond(level: Level, pos: BlockPos, state: BlockState) {
        val facing = state.getValue(SpotlightBlock.FACING).opposite
        val newBrightAirBlocks = HashSet<Int>()
        var targetPos = pos

        for (i in 1..RANGE) {
            targetPos = targetPos.relative(facing)
            val targetBlock = level.getBlockState(targetPos)
            if (isAir(targetBlock)) {
                newBrightAirBlocks.add(i)
            } else if(targetBlock.isSolidRender(level, pos)) {
                break
            }
        }

        for (newI in newBrightAirBlocks) {
            val newPos = pos.offset(facing.normal * newI)
            tryAddLight(level, newPos)
        }

        for (oldI in brightAirBlocks) {
            if (oldI !in newBrightAirBlocks) {
                val oldPos = pos.offset(facing.normal * oldI)
                tryRemoveLight(level, oldPos)
            }
        }

        if(brightAirBlocks != newBrightAirBlocks) {
            brightAirBlocks = newBrightAirBlocks
            setChanged()
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putIntArray("bright_air_blocks", brightAirBlocks.toIntArray())
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        if(tag.contains("bright_air_blocks")) {
            val loadedBrightAirBlocks = tag.getIntArray("bright_air_blocks")
            brightAirBlocks = HashSet()
            for (i in loadedBrightAirBlocks) {
                brightAirBlocks.add(i)
            }
        }
    }
}