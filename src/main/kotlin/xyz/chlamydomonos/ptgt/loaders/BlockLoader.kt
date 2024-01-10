package xyz.chlamydomonos.ptgt.loaders

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject
import xyz.chlamydomonos.ptgt.PortalGates
import xyz.chlamydomonos.ptgt.blocks.*
import xyz.chlamydomonos.ptgt.blocks.bases.BaseBlock
import xyz.chlamydomonos.ptgt.items.bases.BaseBlockItem
import xyz.chlamydomonos.ptgt.loaders.utils.BlockAndItsItem
import kotlin.reflect.KProperty

object BlockLoader : Iterable<BlockAndItsItem<out BaseBlock>> {
    private class BlockAndItsItemDelegate<T : BaseBlock>(
        private val name: String,
        private val block: ObjectHolderDelegate<T>,
        private val item: ObjectHolderDelegate<BaseBlockItem<T>>
    ) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): BlockAndItsItem<T> {
            return BlockAndItsItem(name, block.getValue(thisRef, property), item.getValue(thisRef, property))
        }
    }

    private val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, PortalGates.MODID)
    private val BLOCK_ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, PortalGates.MODID)

    private val BLOCK_AND_ITS_ITEM_MAP = HashMap<String, BlockAndItsItemDelegate<out BaseBlock>>()

    private fun <T : BaseBlock> register(name: String, block: () -> T): BlockAndItsItemDelegate<T> {
        val blockValue = BLOCKS.registerObject(name, block)
        val itemValue = BLOCK_ITEMS.registerObject(name) { BaseBlockItem(blockValue.get()) }
        val result = BlockAndItsItemDelegate(name, blockValue, itemValue)
        BLOCK_AND_ITS_ITEM_MAP[name] = result
        return result
    }

    fun initAll(): Blocks {
        BLOCKS.register(MOD_BUS)
        BLOCK_ITEMS.register(MOD_BUS)
        return Blocks // 愚蠢的Kotlin，如果不在某个地方使用Blocks，那么它就不会被初始化
    }

    override fun iterator(): Iterator<BlockAndItsItem<out BaseBlock>> {
        val mapIterator = BLOCK_AND_ITS_ITEM_MAP.iterator()
        return object : Iterator<BlockAndItsItem<out BaseBlock>> {
            override fun hasNext(): Boolean {
                return mapIterator.hasNext()
            }

            override fun next(): BlockAndItsItem<out BaseBlock> {
                return mapIterator.next().value.getValue(this, this@BlockLoader::BLOCK_AND_ITS_ITEM_MAP)
            }
        }
    }

    operator fun contains(name: String): Boolean {
        return name in BLOCK_AND_ITS_ITEM_MAP
    }

    operator fun get(name: String): BlockAndItsItem<out BaseBlock>? {
        return BLOCK_AND_ITS_ITEM_MAP[name]?.getValue(this, this::BLOCK_AND_ITS_ITEM_MAP)
    }

    @Suppress("unused")
    object Blocks {
        val PORTAL_FRAME by register("portal_frame") { PortalFrameBlock() }
        val PORTAL_STABILIZER by register("portal_stabilizer") { PortalStabilizerBlock() }
        val PORTAL_STABILIZER_CONNECTOR by register("portal_stabilizer_connector") { PortalStabilizerConnectorBlock() }
        val PORTAL_GENERATOR by register("portal_generator") { PortalGeneratorBlock() }
        val SPOTLIGHT by register("spotlight") { SpotlightBlock() }
        val BRIGHT_AIR by BLOCKS.registerObject("bright_air") { BrightAirBlock() }
        val WATERLOGGED_BRIGHT_AIR by BLOCKS.registerObject("waterlogged_bright_air") { WaterloggedBrightAirBlock() }
    }
}