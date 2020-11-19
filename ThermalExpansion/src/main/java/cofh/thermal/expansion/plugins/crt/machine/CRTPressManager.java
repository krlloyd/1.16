package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.core.util.recipes.ThermalRecipe;
import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.expansion.util.recipes.machine.*;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Press")
public class CRTPressManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IFluidStack outputFluid, IIngredient[] ingredients, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        float experience = -1;
        int minTicks = -1;
        List<Ingredient> inputItems = Arrays.stream(ingredients).map(IIngredient::asVanillaIngredient).collect(Collectors.toList());
        List<FluidStack> inputFluids = new ArrayList<>();
        List<ItemStack> outputItems = Arrays.stream(outputs).map(MCWeightedItemStack::getItemStack).map(IItemStack::getInternal).collect(Collectors.toList());
        List<Float> outputItemChances = Arrays.stream(outputs).map(mcWeightedItemStack -> (float) mcWeightedItemStack.getWeight()).collect(Collectors.toList());
        List<FluidStack> outputFluids = outputFluid.getInternal().isEmpty() ? new ArrayList<>() : Collections.singletonList(outputFluid.getInternal());
        PressRecipe recipe = new PressRecipe(resourceLocation, energy, experience, minTicks, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
        
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public IRecipeType<PressRecipe> getRecipeType() {
        return TExpRecipeTypes.RECIPE_PRESS;
    }
    
    @Override
    public void removeRecipe(IItemStack output) {
        removeRecipe(new IItemStack[] {output}, new IFluidStack[0]);
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IItemStack[] itemOutputs, IFluidStack[] fluidOutputs) {
        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, itemOutputs, fluidOutputs));
    }
}
