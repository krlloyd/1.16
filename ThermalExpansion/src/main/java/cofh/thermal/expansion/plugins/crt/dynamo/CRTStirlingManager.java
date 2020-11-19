package cofh.thermal.expansion.plugins.crt.dynamo;

import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalFuelByOutput;
import cofh.thermal.expansion.util.recipes.dynamo.StirlingFuel;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.thermal.StirlingFuel")
public class CRTStirlingManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addFuel(String name, IIngredient ingredient, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        List<Ingredient> items = Collections.singletonList(ingredient.asVanillaIngredient());
        List<FluidStack> fluids = new ArrayList<>();
        StirlingFuel recipe = new StirlingFuel(resourceLocation, energy, items, fluids);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @ZenCodeType.Method
    public void removeFuel(IItemStack outputItem) {
        CraftTweakerAPI.apply(new ActionRemoveThermalFuelByOutput(this, new IItemStack[] {outputItem}));
    }
    
    @Override
    public IRecipeType<StirlingFuel> getRecipeType() {
        return TExpRecipeTypes.FUEL_STIRLING;
    }
}
