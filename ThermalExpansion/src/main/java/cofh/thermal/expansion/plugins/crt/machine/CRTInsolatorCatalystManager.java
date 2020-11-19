package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.*;
import cofh.thermal.expansion.util.recipes.machine.InsolatorCatalyst;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.thermal.InsolatorCatalyst")
public class CRTInsolatorCatalystManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addCatalyst(String name, IIngredient ingredient, float primaryMod, float secondaryMod, float energyMod, float minChance, float useChance) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        InsolatorCatalyst insolatorCatalyst = new InsolatorCatalyst(resourceLocation, ingredient.asVanillaIngredient(), primaryMod, secondaryMod, energyMod, minChance, useChance);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, insolatorCatalyst, ""));
    }
    
    @ZenCodeType.Method
    public void removeCatalyst(IItemStack input) {
        CraftTweakerAPI.apply(new ActionRemoveThermalCatalystByOutput(this, input));
    }
    
    @Override
    public IRecipeType<InsolatorCatalyst> getRecipeType() {
        return TExpRecipeTypes.CATALYST_INSOLATOR;
    }
    
    
}
