package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalCatalystByOutput;
import cofh.thermal.expansion.plugins.crt.base.CRTCatalyst;
import cofh.thermal.expansion.util.recipes.machine.SmelterCatalyst;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.SmelterCatalyst")
public class CRTSmelterCatalystManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addCatalyst(String name, IIngredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
    
        SmelterCatalyst catalyst = new CRTCatalyst(resourceLocation, ingredient, primaryMod, secondaryMod, energyMod, minChance, useChance).smelter();
        CraftTweakerAPI.apply(new ActionAddRecipe(this, catalyst, ""));
    }
    
    @Override
    public void removeRecipe(IItemStack output) {
        removeCatalyst(output);
    }
    
    @ZenCodeType.Method
    public void removeCatalyst(IItemStack input) {
        CraftTweakerAPI.apply(new ActionRemoveThermalCatalystByOutput(this, input));
    }
    
    @Override
    public IRecipeType<SmelterCatalyst> getRecipeType() {
        return TExpRecipeTypes.CATALYST_SMELTER;
    }
    
    
}
