package cofh.thermal.expansion.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import static cofh.core.util.constants.Constants.ID_THERMAL_EXPANSION;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL_EXPANSION)
public class TExpDataGen {

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

        TExpTags.Block blockTags = new TExpTags.Block(gen, exFileHelper);

        gen.addProvider(blockTags);
        gen.addProvider(new TExpTags.Item(gen, blockTags, exFileHelper));
        gen.addProvider(new TExpTags.Fluid(gen, exFileHelper));

        gen.addProvider(new TExpLootTables(gen));
        gen.addProvider(new TExpRecipes(gen));
    }

    private static void registerClientProviders(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        gen.addProvider(new TExpBlockStates(gen, exFileHelper));
        gen.addProvider(new TExpItemModels(gen, exFileHelper));
    }

}
