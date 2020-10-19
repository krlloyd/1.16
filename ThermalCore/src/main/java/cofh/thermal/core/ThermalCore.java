package cofh.thermal.core;

import cofh.core.client.renderer.entity.SpriteRendererCoFH;
import cofh.core.client.renderer.entity.TNTRendererCoFH;
import cofh.core.registries.DeferredRegisterCoFH;
import cofh.thermal.core.client.gui.device.DeviceHiveExtractorScreen;
import cofh.thermal.core.client.gui.device.DeviceTreeExtractorScreen;
import cofh.thermal.core.client.gui.workbench.TinkerBenchScreen;
import cofh.thermal.core.client.renderer.entity.*;
import cofh.thermal.core.common.ThermalConfig;
import cofh.thermal.core.common.ThermalRecipeManagers;
import cofh.thermal.core.init.*;
import cofh.thermal.core.util.loot.TileNBTSync;
import cofh.thermal.core.world.ThermalWorld;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cofh.core.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.common.ThermalFeatures.*;
import static cofh.thermal.core.init.TCoreIDs.*;
import static cofh.thermal.core.init.TCoreReferences.*;

@Mod(ID_THERMAL)
public class ThermalCore {

    public static final Logger LOG = LogManager.getLogger(ID_THERMAL);

    public static final DeferredRegisterCoFH<Block> BLOCKS = DeferredRegisterCoFH.create(ForgeRegistries.BLOCKS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Fluid> FLUIDS = DeferredRegisterCoFH.create(ForgeRegistries.FLUIDS, ID_THERMAL);
    public static final DeferredRegisterCoFH<Item> ITEMS = DeferredRegisterCoFH.create(ForgeRegistries.ITEMS, ID_THERMAL);

    public static final DeferredRegisterCoFH<ContainerType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.CONTAINERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<EntityType<?>> ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.ENTITIES, ID_THERMAL);
    public static final DeferredRegisterCoFH<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_SERIALIZERS, ID_THERMAL);
    public static final DeferredRegisterCoFH<SoundEvent> SOUND_EVENTS = DeferredRegisterCoFH.create(ForgeRegistries.SOUND_EVENTS, ID_THERMAL);
    public static final DeferredRegisterCoFH<TileEntityType<?>> TILE_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.TILE_ENTITIES, ID_THERMAL);

    static {
        TCoreBlocks.register();
        TCoreItems.register();
        TCoreFluids.register();

        TCoreEntities.register();
        TCoreSounds.register();

        TCoreRecipeManagers.register();
        TCoreRecipeSerializers.register();
        TCoreRecipeTypes.register();
    }

    public ThermalCore() {

        setFeatureFlags();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
        MinecraftForge.EVENT_BUS.addListener(this::serverStopping);
        MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListener);
        MinecraftForge.EVENT_BUS.addListener(this::recipesUpdated);

        BLOCKS.register(modEventBus);
        CONTAINERS.register(modEventBus);
        ENTITIES.register(modEventBus);
        FLUIDS.register(modEventBus);
        ITEMS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);

        ThermalConfig.register();
    }

    private void setFeatureFlags() {

        setFeature(FLAG_RESOURCE_NITER, true);
        setFeature(FLAG_RESOURCE_SULFUR, true);

        setFeature(FLAG_RESOURCE_COPPER, true);
        setFeature(FLAG_RESOURCE_TIN, true);
        setFeature(FLAG_RESOURCE_LEAD, true);
        setFeature(FLAG_RESOURCE_SILVER, true);
        setFeature(FLAG_RESOURCE_NICKEL, true);

        setFeature(FLAG_AREA_AUGMENTS, true);
        setFeature(FLAG_STORAGE_AUGMENTS, true);
        setFeature(FLAG_UPGRADE_AUGMENTS, true);

        setFeature(ID_TINKER_BENCH, true);
    }

    // region INITIALIZATION
    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(TCoreBlocks::setup);
        event.enqueueWork(TCoreItems::setup);
        event.enqueueWork(TCoreEntities::setup);

        TileNBTSync.setup();
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        registerGuiFactories();
        registerRenderLayers();
        registerItemModelProperties();
        registerEntityRenderingHandlers();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    private void serverAboutToStart(FMLServerAboutToStartEvent event) {

        // TODO: Temporary
        ThermalWorld.setup();
    }

    private void serverStarted(final FMLServerStartedEvent event) {

    }

    private void serverStopping(final FMLServerStoppingEvent event) {

    }

    private void serverStopped(final FMLServerStoppedEvent event) {

        ThermalRecipeManagers.instance().setServerRecipeManager(null);
    }

    private void addReloadListener(final AddReloadListenerEvent event) {

        event.getDataPackRegistries().getRecipeManager();

        event.addListener(
                new ReloadListener<Void>() {

                    @Override
                    protected Void prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {

                        ThermalRecipeManagers.instance().setServerRecipeManager(event.getDataPackRegistries().getRecipeManager());
                        return null;
                    }

                    @Override
                    protected void apply(Void nothing, IResourceManager resourceManagerIn, IProfiler profilerIn) {

                        ThermalRecipeManagers.instance().refreshServer();
                    }
                }
        );
    }

    private void recipesUpdated(final RecipesUpdatedEvent event) {

        ThermalRecipeManagers.instance().refreshClient(event.getRecipeManager());
    }
    // endregion

    // region HELPERS
    private void registerGuiFactories() {

        ScreenManager.registerFactory(DEVICE_HIVE_EXTRACTOR_CONTAINER, DeviceHiveExtractorScreen::new);
        ScreenManager.registerFactory(DEVICE_TREE_EXTRACTOR_CONTAINER, DeviceTreeExtractorScreen::new);

        ScreenManager.registerFactory(TINKER_BENCH_CONTAINER, TinkerBenchScreen::new);
    }

    private void registerRenderLayers() {

        RenderType cutout = RenderType.getCutout();

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_OBSIDIAN_GLASS), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_SIGNALUM_GLASS), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_LUMIUM_GLASS), cutout);
        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_ENDERIUM_GLASS), cutout);

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_MACHINE_FRAME), cutout);

        RenderTypeLookup.setRenderLayer(BLOCKS.get(ID_DEVICE_TREE_EXTRACTOR), cutout);
    }

    private void registerItemModelProperties() {

        ItemModelsProperties.registerProperty(ITEMS.get("copper_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("tin_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("lead_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("silver_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("nickel_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());

        ItemModelsProperties.registerProperty(ITEMS.get("bronze_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("electrum_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("invar_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("constantan_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());

        ItemModelsProperties.registerProperty(ITEMS.get("signalum_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("lumium_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("enderium_plate"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());

        ItemModelsProperties.registerProperty(ITEMS.get("copper_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("tin_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("lead_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("silver_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("nickel_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());

        ItemModelsProperties.registerProperty(ITEMS.get("bronze_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("electrum_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("invar_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("constantan_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());

        ItemModelsProperties.registerProperty(ITEMS.get("signalum_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("lumium_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());
        ItemModelsProperties.registerProperty(ITEMS.get("enderium_coin"), new ResourceLocation("count"), (stack, world, living) -> ((float) stack.getCount()) / stack.getMaxStackSize());

        // TODO: 1.16 Fix.
        //        ItemModelsProperties.registerProperty(ITEMS.get("detonator"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));
        //        ItemModelsProperties.registerProperty(ITEMS.get("detonator"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));

        ItemModelsProperties.registerProperty(ITEMS.get("explosive_grenade"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));

        ItemModelsProperties.registerProperty(ITEMS.get("phyto_grenade"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));

        ItemModelsProperties.registerProperty(ITEMS.get("fire_grenade"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));
        ItemModelsProperties.registerProperty(ITEMS.get("earth_grenade"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));
        ItemModelsProperties.registerProperty(ITEMS.get("ice_grenade"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));
        ItemModelsProperties.registerProperty(ITEMS.get("lightning_grenade"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));

        ItemModelsProperties.registerProperty(ITEMS.get("nuke_grenade"), new ResourceLocation("thrown"), (stack, world, living) -> (stack.getDamage() > 0 ? 1.0F : 0.0F));
    }

    private void registerEntityRenderingHandlers() {

        RenderingRegistry.registerEntityRenderingHandler(BASALZ_ENTITY, BasalzRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLITZ_ENTITY, BlitzRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLIZZ_ENTITY, BlizzRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(BASALZ_PROJECTILE_ENTITY, BasalzProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLITZ_PROJECTILE_ENTITY, BlitzProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BLIZZ_PROJECTILE_ENTITY, BlizzProjectileRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EXPLOSIVE_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(PHYTO_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(FIRE_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(EARTH_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(ICE_GRENADE_ENTITY, SpriteRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(LIGHTNING_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(NUKE_GRENADE_ENTITY, SpriteRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(PHYTO_TNT_ENTITY, TNTRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(FIRE_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(EARTH_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(ICE_TNT_ENTITY, TNTRendererCoFH::new);
        RenderingRegistry.registerEntityRenderingHandler(LIGHTNING_TNT_ENTITY, TNTRendererCoFH::new);

        RenderingRegistry.registerEntityRenderingHandler(NUKE_TNT_ENTITY, TNTRendererCoFH::new);
    }
    // endregion
}
