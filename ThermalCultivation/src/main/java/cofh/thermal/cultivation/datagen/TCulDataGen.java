package cofh.thermal.cultivation.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import static cofh.core.util.constants.Constants.ID_THERMAL_CULTIVATION;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL_CULTIVATION)
public class TCulDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        if (event.includeServer()) {
            registerServerProviders(event);
        }
        if (event.includeClient()) {
            registerClientProviders(event);
        }
    }

    private static void registerServerProviders(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        TCulTags.Block blockTags = new TCulTags.Block(gen, exFileHelper);

        gen.addProvider(blockTags);
        gen.addProvider(new TCulTags.Item(gen, blockTags, exFileHelper));
        gen.addProvider(new TCulTags.Fluid(gen, exFileHelper));

        gen.addProvider(new TCulLootTables(gen));
        gen.addProvider(new TCulRecipes(gen));
    }

    private static void registerClientProviders(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        gen.addProvider(new TCulBlockStates(gen, exFileHelper));
        gen.addProvider(new TCulItemModels(gen, exFileHelper));
    }

}
