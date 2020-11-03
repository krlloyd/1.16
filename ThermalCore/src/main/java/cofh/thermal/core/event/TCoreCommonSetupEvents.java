package cofh.thermal.core.event;

import cofh.thermal.core.common.ThermalRecipeManagers;
import cofh.thermal.core.world.biome.ThermalBiomeFeatures;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cofh.core.util.constants.Constants.ID_THERMAL;

@Mod.EventBusSubscriber(modid = ID_THERMAL)
public class TCoreCommonSetupEvents {

    private TCoreCommonSetupEvents() {

    }

    //    @SubscribeEvent
    //    public static void serverAboutToStart(FMLServerAboutToStartEvent event) {
    //
    //    }
    //
    //    @SubscribeEvent
    //    public static void serverStarted(final FMLServerStartedEvent event) {
    //
    //    }
    //
    //    @SubscribeEvent
    //    public static void serverStopping(final FMLServerStoppingEvent event) {
    //
    //    }
    //
    //    @SubscribeEvent
    //    public static void serverStopped(final FMLServerStoppedEvent event) {
    //
    //    }

    @SubscribeEvent
    public static void addReloadListener(final AddReloadListenerEvent event) {

        event.addListener(
                new ReloadListener<Void>() {

                    @Override
                    protected Void prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {

                        ThermalRecipeManagers.instance().setServerRecipeManager(event.getDataPackRegistries().getRecipeManager());
                        return null;
                    }

                    @Override
                    protected void apply(Void nothing, IResourceManager resourceManagerIn, IProfiler profilerIn) {

                    }
                }
        );
    }

    // Recipes reload during TagsUpdatedEvent on Server side.
    @SubscribeEvent
    public static void tagsUpdated(final TagsUpdatedEvent.VanillaTagTypes event) {

        ThermalRecipeManagers.instance().refreshServer();
    }

    // Recipes reload during RecipesUpdatedEvent on Client side.
    @SubscribeEvent
    public static void recipesUpdated(final RecipesUpdatedEvent event) {

        ThermalRecipeManagers.instance().refreshClient(event.getRecipeManager());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void biomeLoadingAdd(final BiomeLoadingEvent event) {

        ThermalBiomeFeatures.addOreGeneration(event);
        ThermalBiomeFeatures.addHostileSpawns(event);
    }
    // endregion
}
