package cofh.thermal.core.datagen;

import cofh.thermal.core.util.loot.TileNBTSync;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import static cofh.core.util.constants.Constants.ID_THERMAL;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID_THERMAL)
public class TCoreDataGen {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {

        TileNBTSync.setup();

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

        TCoreTags.Block blockTags = new TCoreTags.Block(gen, exFileHelper);

        gen.addProvider(blockTags);
        gen.addProvider(new TCoreTags.Item(gen, blockTags, exFileHelper));
        gen.addProvider(new TCoreTags.Fluid(gen, exFileHelper));

        gen.addProvider(new TCoreLootTables(gen));
        gen.addProvider(new TCoreRecipes(gen));
    }

    private static void registerClientProviders(GatherDataEvent event) {

        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        gen.addProvider(new TCoreBlockStates(gen, exFileHelper));
        gen.addProvider(new TCoreItemModels(gen, exFileHelper));
    }

}
