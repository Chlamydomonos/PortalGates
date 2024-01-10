package xyz.chlamydomonos.ptgt.datagen

import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import xyz.chlamydomonos.ptgt.PortalGates
import xyz.chlamydomonos.ptgt.loaders.BlockLoader
import xyz.chlamydomonos.ptgt.loaders.ItemLoader

class ItemGenerator(generator: DataGenerator?, existingFileHelper: ExistingFileHelper?) :
    ItemModelProvider(generator, PortalGates.MODID, existingFileHelper) {

    companion object {
        val GENERATED = ResourceLocation("minecraft", "item/generated")
        val HANDHELD = ResourceLocation("minecraft", "item/handheld")
    }

    override fun registerModels() {
        for (blockAndItsItem in BlockLoader) {
            val name = blockAndItsItem.name
            withExistingParent(name, modLoc("block/$name"))
        }

        for (itemWithHandheldInfo in ItemLoader) {
            val name = itemWithHandheldInfo.name

            if(itemWithHandheldInfo.isHandheld) {
                withExistingParent(name, HANDHELD).texture("layer0", modLoc("item/$name"))
            } else {
                withExistingParent(name, GENERATED).texture("layer0", modLoc("item/$name"))
            }
        }
    }
}