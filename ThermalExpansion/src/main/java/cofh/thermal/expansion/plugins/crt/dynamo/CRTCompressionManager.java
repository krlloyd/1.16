package cofh.thermal.expansion.plugins.crt.dynamo;

import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalFuelByOutput;
import cofh.thermal.expansion.util.recipes.dynamo.CompressionFuel;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.thermal.CompressionFuel")
public class CRTCompressionManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addFuel(String name, IFluidStack ingredient, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        List<Ingredient> items = new ArrayList<>();
        List<FluidStack> fluids = Collections.singletonList(ingredient.getInternal());
        CompressionFuel recipe = new CompressionFuel(resourceLocation, energy, items, fluids);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public void removeRecipe(IItemStack output) {
        throw new IllegalArgumentException("Compression Fuel only works with fluids! Please provide an IFluidStack");
    }
    
    @ZenCodeType.Method
    public void removeFuel(IFluidStack outputFluid) {
        CraftTweakerAPI.apply(new ActionRemoveThermalFuelByOutput(this, new IFluidStack[] {outputFluid}));
    }
    
    @Override
    public IRecipeType<CompressionFuel> getRecipeType() {
        return TExpRecipeTypes.FUEL_COMPRESSION;
    }
}
