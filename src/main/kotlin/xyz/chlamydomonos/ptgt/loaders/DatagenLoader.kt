package xyz.chlamydomonos.ptgt.loaders

import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import xyz.chlamydomonos.ptgt.PortalGates
import xyz.chlamydomonos.ptgt.datagen.ItemGenerator

@EventBusSubscriber(modid = PortalGates.MODID, bus = EventBusSubscriber.Bus.MOD)
object DatagenLoader {
    @SubscribeEvent
    fun runDatagen(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper

        if (event.includeClient()) {
            event.generator.addProvider(true, ItemGenerator(generator, existingFileHelper))    // event.getGenerator().addProvider(ItemGenerator(event.getGenerator(), event.getExistingFileHelper()))
        }

        event.generator.run()
    }
}