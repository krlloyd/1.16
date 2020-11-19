package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.core.util.recipes.ThermalRecipe;
import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.expansion.util.recipes.machine.FurnaceRecipe;
import cofh.thermal.expansion.util.recipes.machine.*;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Crucible")
public class CRTCrucibleManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addRecipe(String name, IFluidStack output, IIngredient ingredient, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        float experience = -1;
        int minTicks = -1;
        List<Ingredient> inputItems = Collections.singletonList(ingredient.asVanillaIngredient());
        List<FluidStack> inputFluids = new ArrayList<>();
        List<ItemStack> outputItems = new ArrayList<>();
        List<Float> outputItemChances = new ArrayList<>();
        List<FluidStack> outputFluids = Collections.singletonList(output.getInternal());
        CrucibleRecipe recipe = new CrucibleRecipe(resourceLocation, energy, experience, minTicks, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
        
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public IRecipeType<CrucibleRecipe> getRecipeType() {
        return TExpRecipeTypes.RECIPE_CRUCIBLE;
    }
    
    public void removeRecipe(IItemStack output) {
        throw new IllegalArgumentException("The Crucible only outputs fluids! Please provide an IFluidStack");
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IFluidStack output) {
        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, new IFluidStack[]{output}));
    }
    
    
}
