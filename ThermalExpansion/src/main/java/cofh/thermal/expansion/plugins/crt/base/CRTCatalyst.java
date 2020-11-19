package cofh.thermal.expansion.plugins.crt.base;

import cofh.thermal.expansion.util.recipes.machine.*;
import com.blamejared.crafttweaker.api.item.IIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class CRTCatalyst {
    
    private final ResourceLocation name;
    private final Ingredient ingredient;
    private final float primaryMod;
    private final float secondaryMod;
    private final float energyMod;
    private final float minChance;
    private final float useChance;
    
    public CRTCatalyst(ResourceLocation name, IIngredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {
        this.name = name;
        this.ingredient = ingredient.asVanillaIngredient();
        this.primaryMod = primaryMod;
        this.secondaryMod = secondaryMod;
        this.energyMod = energyMod;
        this.minChance = minChance;
        this.useChance = useChance;
    }
    
    public InsolatorCatalyst insolator() {
        return new InsolatorCatalyst(name, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }
    
    public PulverizerCatalyst pulverizer() {
        return new PulverizerCatalyst(name, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }
    
    public SmelterCatalyst smelter() {
        return new SmelterCatalyst(name, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance);
    }
    
}