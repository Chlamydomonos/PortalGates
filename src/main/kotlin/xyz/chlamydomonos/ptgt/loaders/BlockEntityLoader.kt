package xyz.chlamydomonos.ptgt.loaders

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject
import xyz.chlamydomonos.ptgt.PortalGates
import xyz.chlamydomonos.ptgt.blockentities.*
import xyz.chlamydomonos.ptgt.blocks.bases.BaseBlock
import xyz.chlamydomonos.ptgt.loaders.utils.BlockAndItsItem

object BlockEntityLoader {
    private val BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PortalGates.MODID)

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun <T : BlockEntity> register(
        name: String,
        block: () -> BlockAndItsItem<out BaseBlock>,
        supplier: (BlockPos, BlockState) -> T
    ): ObjectHolderDelegate<BlockEntityType<T>> {
        return BLOCK_ENTITIES.registerObject(name) {
            BlockEntityType.Builder.of(supplier, block().block).build(null)
        }
    }

    fun initAll(): BlockEntities {
        BLOCK_ENTITIES.register(MOD_BUS)
        return BlockEntities
    }

    object BlockEntities {
        val PORTAL_GENERATOR by register("portal_generator", { BlockLoader.Blocks.PORTAL_GENERATOR }) {
                blockPos, blockState -> PortalGeneratorBlockEntity(blockPos, blockState)
        }

        val SPOTLIGHT by register("spotlight", { BlockLoader.Blocks.SPOTLIGHT }) {
            blockPos, blockState -> SpotlightBlockEntity(blockPos, blockState)
        }
    }
}