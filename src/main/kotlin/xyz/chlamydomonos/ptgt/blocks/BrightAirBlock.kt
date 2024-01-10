package xyz.chlamydomonos.ptgt.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.AirBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid

open class BrightAirBlock : AirBlock(Properties.copy(Blocks.AIR)) {
    override fun canStickTo(state: BlockState?, other: BlockState?): Boolean {
        return false
    }

    override fun isAir(state: BlockState): Boolean {
        return true
    }

    override fun canEntityDestroy(state: BlockState?, level: BlockGetter?, pos: BlockPos?, entity: Entity?): Boolean {
        return false
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun canBeReplaced(pState: BlockState, pFluid: Fluid): Boolean {
        return true
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun canBeReplaced(pState: BlockState, pUseContext: BlockPlaceContext): Boolean {
        return true
    }

    override fun getLightEmission(state: BlockState?, level: BlockGetter?, pos: BlockPos?): Int {
        return 15
    }
}