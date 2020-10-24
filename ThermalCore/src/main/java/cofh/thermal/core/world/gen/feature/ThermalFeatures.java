package cofh.thermal.core.world.gen.feature;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

import static cofh.core.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreIDs.*;

public class ThermalFeatures {

    private ThermalFeatures() {

    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {

        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(ID_THERMAL, key), configuredFeature);
    }

    public static void setup() {

        ORE_APATITE = register("ore_apatite", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_APATITE_ORE).getDefaultState(), 13))
                .withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(16, 16)))
                .func_242728_a()
                .func_242731_b(4));

        ORE_CINNABAR = register("ore_cinnabar", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_CINNABAR_ORE).getDefaultState(), 5))
                .withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(16, 16)))
                .func_242728_a()
                .func_242731_b(1));

        ORE_NITER = register("ore_niter", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_NITER_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(40, 12)))
                .func_242728_a()
                .func_242731_b(2));

        ORE_SULFUR = register("ore_sulfur", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_SULFUR_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(24, 12)))
                .func_242728_a()
                .func_242731_b(2));

        ORE_COPPER = register("ore_copper", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_COPPER_ORE).getDefaultState(), 9))
                .withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(40, 0, 128)))
                .func_242728_a().func_242731_b(8));

        ORE_TIN = register("ore_tin", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_TIN_ORE).getDefaultState(), 9))
                .withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(20, 0, 60)))
                .func_242728_a().func_242731_b(8));

        ORE_LEAD = register("ore_lead", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_LEAD_ORE).getDefaultState(), 8))
                .withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(0, 0, 32)))
                .func_242728_a().func_242731_b(2));

        ORE_SILVER = register("ore_silver", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_SILVER_ORE).getDefaultState(), 8))
                .withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(0, 0, 32)))
                .func_242728_a().func_242731_b(2));

        ORE_NICKEL = register("ore_nickel", Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_NICKEL_ORE).getDefaultState(), 8))
                .withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(0, 0, 128)))
                .func_242728_a().func_242731_b(2));
    }

    public static ConfiguredFeature<?, ?> ORE_APATITE;
    public static ConfiguredFeature<?, ?> ORE_CINNABAR;
    public static ConfiguredFeature<?, ?> ORE_NITER;
    public static ConfiguredFeature<?, ?> ORE_SULFUR;

    public static ConfiguredFeature<?, ?> ORE_COPPER;
    public static ConfiguredFeature<?, ?> ORE_TIN;
    public static ConfiguredFeature<?, ?> ORE_LEAD;
    public static ConfiguredFeature<?, ?> ORE_SILVER;
    public static ConfiguredFeature<?, ?> ORE_NICKEL;

    public static ConfiguredFeature<?, ?> ORE_RUBY;
    public static ConfiguredFeature<?, ?> ORE_SAPPHIRE;

}
