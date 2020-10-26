package cofh.thermal.core.world.gen.feature;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.init.TCoreIDs.*;

public class ThermalFeatures {

    private ThermalFeatures() {

    }

    public static void setup() {

        ORE_APATITE = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_APATITE_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242910_o.configure(depthRange(48, 24)))
                .func_242728_a()
                .func_242731_b(4);

        ORE_CINNABAR = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_CINNABAR_ORE).getDefaultState(), 5))
                .withPlacement(Placement.field_242910_o.configure(depthRange(16, 16)))
                .func_242728_a()
                .func_242731_b(1);

        ORE_NITER = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_NITER_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242910_o.configure(depthRange(40, 12)))
                .func_242728_a()
                .func_242731_b(2);

        ORE_SULFUR = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_SULFUR_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242910_o.configure(depthRange(24, 12)))
                .func_242728_a()
                .func_242731_b(2);

        ORE_COPPER = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_COPPER_ORE).getDefaultState(), 9))
                .withPlacement(Placement.field_242907_l.configure(topRange(40, 80)))
                .func_242728_a().func_242731_b(8);

        ORE_TIN = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_TIN_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242907_l.configure(topRange(20, 60)))
                .func_242728_a().func_242731_b(4);

        ORE_LEAD = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_LEAD_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242907_l.configure(topRange(0, 40)))
                .func_242728_a().func_242731_b(2);

        ORE_SILVER = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_SILVER_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242907_l.configure(topRange(0, 40)))
                .func_242728_a().func_242731_b(2);

        ORE_NICKEL = Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_NICKEL_ORE).getDefaultState(), 7))
                .withPlacement(Placement.field_242907_l.configure(topRange(0, 60)))
                .func_242728_a().func_242731_b(2);
    }

    private static DepthAverageConfig depthRange(int base, int spread) {

        return new DepthAverageConfig(base, spread);
    }

    private static TopSolidRangeConfig topRange(int min, int max) {

        return new TopSolidRangeConfig(min, min, max);
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
