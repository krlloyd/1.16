package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.core.util.recipes.ThermalRecipe;
import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.expansion.util.recipes.machine.RefineryRecipe;
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
@ZenCodeType.Name("mods.thermal.Refinery")
public class CRTRefineryManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack itemOutput, IFluidStack[] fluidsOutput, IFluidStack inputFluid, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        float experience = -1;
        int minTicks = -1;
        List<Ingredient> inputItems = new ArrayList<>();
        List<FluidStack> inputFluids = inputFluid.getInternal().isEmpty() ? new ArrayList<>() : Collections.singletonList(inputFluid.getInternal());
        List<ItemStack> outputItems = itemOutput.getItemStack().isEmpty() ? new ArrayList<>() : Collections.singletonList(itemOutput.getItemStack().getInternal());
        List<Float> outputItemChances = itemOutput.getItemStack().isEmpty() ? new ArrayList<>() : Collections.singletonList((float) itemOutput.getWeight());
        List<FluidStack> outputFluids = Arrays.stream(fluidsOutput).map(IFluidStack::getInternal).filter(fluidStack -> !fluidStack.isEmpty()).collect(Collectors.toList());
        
        RefineryRecipe recipe = new RefineryRecipe(resourceLocation, energy, experience, minTicks, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public IRecipeType<RefineryRecipe> getRecipeType() {
        return TExpRecipeTypes.RECIPE_REFINERY;
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
