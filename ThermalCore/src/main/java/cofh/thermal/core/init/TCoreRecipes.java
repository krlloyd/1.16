package cofh.thermal.core.init;

import cofh.core.util.recipes.SerializableRecipeType;
import cofh.thermal.core.util.managers.device.TreeExtractorManager;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoost;
import cofh.thermal.core.util.recipes.device.TreeExtractorBoostSerializer;
import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import cofh.thermal.core.util.recipes.device.TreeExtractorMappingSerializer;
import net.minecraft.util.ResourceLocation;

import static cofh.core.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.RECIPE_SERIALIZERS;
import static cofh.thermal.core.common.ThermalRecipeManagers.registerManager;

public class TCoreRecipes {

    private TCoreRecipes() {

    }

    public static void register() {

        registerManager(TreeExtractorManager.instance());

        RECIPE_SERIALIZERS.register(ID_BOOST_TREE_EXTRACTOR, TreeExtractorBoostSerializer::new);
        RECIPE_SERIALIZERS.register(ID_MAPPING_TREE_EXTRACTOR, TreeExtractorMappingSerializer::new);

        // TODO: Convert when a ForgeRegistry is added.
        // Recipes are self-registered as they do not currently have a proper Forge Registry.
        BOOST_TREE_EXTRACTOR.register();
        MAPPING_TREE_EXTRACTOR.register();
    }

    // region RECIPES
    public static final ResourceLocation ID_BOOST_TREE_EXTRACTOR = new ResourceLocation(ID_THERMAL, "tree_extractor_boost");
    public static final ResourceLocation ID_MAPPING_TREE_EXTRACTOR = new ResourceLocation(ID_THERMAL, "tree_extractor");

    public static final SerializableRecipeType<TreeExtractorBoost> BOOST_TREE_EXTRACTOR = new SerializableRecipeType<>(ID_BOOST_TREE_EXTRACTOR);
    public static final SerializableRecipeType<TreeExtractorMapping> MAPPING_TREE_EXTRACTOR = new SerializableRecipeType<>(ID_MAPPING_TREE_EXTRACTOR);
    // endregion
}
