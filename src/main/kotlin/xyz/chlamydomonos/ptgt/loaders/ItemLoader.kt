package xyz.chlamydomonos.ptgt.loaders

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject
import xyz.chlamydomonos.ptgt.PortalGates
import xyz.chlamydomonos.ptgt.items.*
import xyz.chlamydomonos.ptgt.items.bases.BaseItem
import xyz.chlamydomonos.ptgt.loaders.utils.ItemWithHandheldInfo
import kotlin.reflect.KProperty

object ItemLoader : Iterable<ItemWithHandheldInfo<out BaseItem>> {
    private class ItemWithHandheldInfoDelegate<T : BaseItem>(
        private val name: String,
        private val item: ObjectHolderDelegate<T>,
        private val isHandheld: Boolean
    ) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): ItemWithHandheldInfo<T> {
            return ItemWithHandheldInfo(name, item.get(), isHandheld)
        }
    }

    private val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PortalGates.MODID)

    private val ITEM_MAP = HashMap<String, ItemWithHandheldInfoDelegate<out BaseItem>>()

    @Suppress("SameParameterValue")
    private fun <T : BaseItem> register(
        name: String,
        isHandheld: Boolean = false,
        item: () -> T
    ): ItemWithHandheldInfoDelegate<T> {
        val itemValue = ITEMS.registerObject(name, item)
        val result = ItemWithHandheldInfoDelegate(name, itemValue, isHandheld)
        ITEM_MAP[name] = result
        return result
    }

    fun initAll(): Items {
        ITEMS.register(MOD_BUS)
        return Items // 愚蠢的Kotlin，如果不在某个地方使用Items，那么它就不会被初始化
    }

    override fun iterator(): Iterator<ItemWithHandheldInfo<out BaseItem>> {
        return object : Iterator<ItemWithHandheldInfo<out BaseItem>> {
            private val mapIterator = ITEM_MAP.iterator()

            override fun hasNext(): Boolean {
                return mapIterator.hasNext()
            }

            override fun next(): ItemWithHandheldInfo<out BaseItem> {
                return mapIterator.next().value.getValue(this, this@ItemLoader::ITEM_MAP)
            }
        }
    }

    operator fun contains(name: String): Boolean {
        return ITEM_MAP.containsKey(name)
    }

    operator fun get(name: String): BaseItem? {
        return ITEM_MAP[name]?.getValue(this, this@ItemLoader::ITEM_MAP)?.item
    }

    @Suppress("unused")
    object Items {
        val DEBUG_STICK by register("debug_stick", true) { DebugStickItem() }
        val WRENCH by register("wrench", true) { WrenchItem() }
    }
}