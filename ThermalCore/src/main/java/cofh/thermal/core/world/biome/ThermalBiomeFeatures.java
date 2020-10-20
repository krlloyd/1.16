package cofh.thermal.core.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

import static cofh.thermal.core.init.TCoreReferences.*;

public class ThermalBiomeFeatures {

    private ThermalBiomeFeatures() {

    }

    public static void addOreGeneration(BiomeLoadingEvent event) {

        //import static cofh.thermal.core.ThermalCore.BLOCKS;
        //import static cofh.thermal.core.common.ThermalFeatures.*;
        //import static cofh.thermal.core.init.TCoreIDs.*;

        //        if (getFeature(FLAG_RESOURCE_APATITE).getAsBoolean() && getFeature(FLAG_GEN_APATITE).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_APATITE_ORE).getDefaultState(), 13)).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(4, 16, 16))));
        //        }
        //        if (getFeature(FLAG_RESOURCE_CINNABAR).getAsBoolean() && getFeature(FLAG_GEN_CINNABAR).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_CINNABAR_ORE).getDefaultState(), 5)).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(1, 16, 16))));
        //        }
        //        if (getFeature(FLAG_RESOURCE_NITER).getAsBoolean() && getFeature(FLAG_GEN_NITER).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_NITER_ORE).getDefaultState(), 7)).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(2, 40, 12))));
        //        }
        //        if (getFeature(FLAG_RESOURCE_SULFUR).getAsBoolean() && getFeature(FLAG_GEN_SULFUR).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_SULFUR_ORE).getDefaultState(), 7)).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(2, 24, 12))));
        //        }
        //
        //        if (getFeature(FLAG_RESOURCE_COPPER).getAsBoolean() && getFeature(FLAG_GEN_COPPER).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_COPPER_ORE).getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 40, 0, 60))));
        //        }
        //        if (getFeature(FLAG_RESOURCE_TIN).getAsBoolean() && getFeature(FLAG_GEN_TIN).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_TIN_ORE).getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 20, 0, 40))));
        //        }
        //        if (getFeature(FLAG_RESOURCE_LEAD).getAsBoolean() && getFeature(FLAG_GEN_LEAD).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_LEAD_ORE).getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 32))));
        //        }
        //        if (getFeature(FLAG_RESOURCE_SILVER).getAsBoolean() && getFeature(FLAG_GEN_SILVER).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_SILVER_ORE).getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 32))));
        //        }
        //        if (getFeature(FLAG_RESOURCE_NICKEL).getAsBoolean() && getFeature(FLAG_GEN_NICKEL).getAsBoolean()) {
        //            biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BLOCKS.get(ID_NICKEL_ORE).getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 128))));
        //        }
    }

    public static void addHostileSpawns(BiomeLoadingEvent event) {

        MobSpawnInfoBuilder spawns = event.getSpawns();
        List<MobSpawnInfo.Spawners> monsterSpawns = spawns.getSpawner(EntityClassification.MONSTER);

        if (monsterSpawns.isEmpty()) {
            return;
        }
        Biome.Category category = event.getCategory();
        Biome.Climate climate = event.getClimate();

        if (isOverworldBiome(category)) {
            if (category == Biome.Category.EXTREME_HILLS || category == Biome.Category.MESA) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BASALZ_ENTITY, 10, 1, 3));
            }
            if (category == Biome.Category.DESERT || category == Biome.Category.MESA || category == Biome.Category.SAVANNA) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BLITZ_ENTITY, 10, 1, 3));
            }
            if (climate.precipitation == Biome.RainType.SNOW & climate.temperature <= 0.3F) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BLIZZ_ENTITY, 10, 1, 3));
            }
        }
    }

    public static boolean isOverworldBiome(Biome.Category category) {

        return category != Biome.Category.NONE && category != Biome.Category.THEEND && category != Biome.Category.NETHER;
    }

}
