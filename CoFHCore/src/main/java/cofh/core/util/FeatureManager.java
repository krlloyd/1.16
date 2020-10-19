package cofh.core.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.loot.LootConditionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.function.BooleanSupplier;

import static cofh.core.util.constants.Constants.FALSE;
import static cofh.core.util.constants.Constants.TRUE;

public class FeatureManager {

    private static final Object2ObjectOpenHashMap<String, BooleanSupplier> FEATURES = new Object2ObjectOpenHashMap<>(64);

    public LootConditionType flagConditionType;

    public FeatureManager(String modId) {

        CraftingHelper.register(new FeatureRecipeCondition.Serializer(this, new ResourceLocation(modId, "flag")));
        flagConditionType = new LootConditionType(new FeatureLootCondition.Serializer(this));
        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(modId, "flag"), flagConditionType);
    }

    private BooleanSupplier getFeatureRaw(String flag) {

        FEATURES.putIfAbsent(flag, FALSE);
        return FEATURES.get(flag);
    }

    public synchronized void setFeature(String flag, boolean enable) {

        FEATURES.put(flag, enable ? TRUE : FALSE);
    }

    public synchronized void setFeature(String flag, BooleanSupplier condition) {

        FEATURES.put(flag, condition == null ? FALSE : condition);
    }

    public BooleanSupplier getFeature(String flag) {

        return getFeatureRaw(flag);
    }

}
