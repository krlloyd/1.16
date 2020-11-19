package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.expansion.util.recipes.machine.CentrifugeRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Centrifuge")
public class CRTCentrifugeManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IFluidStack outputFluid, IIngredient ingredient, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        float experience = -1;
        int minTicks = -1;
        List<Ingredient> inputItems = Collections.singletonList(ingredient.asVanillaIngredient());
        List<FluidStack> inputFluids = new ArrayList<>();
        List<ItemStack> outputItems = Arrays.stream(outputs).map(MCWeightedItemStack::getItemStack).map(IItemStack::getInternal).collect(Collectors.toList());
        List<Float> outputItemChances = Arrays.stream(outputs).map(mcWeightedItemStack -> (float) mcWeightedItemStack.getWeight()).collect(Collectors.toList());
        List<FluidStack> outputFluids = outputFluid.getInternal().isEmpty() ? new ArrayList<>() : Collections.singletonList(outputFluid.getInternal());
        CentrifugeRecipe recipe = new CentrifugeRecipe(resourceLocation, energy, experience, minTicks, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
        
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public IRecipeType<CentrifugeRecipe> getRecipeType() {
        return TExpRecipeTypes.RECIPE_CENTRIFUGE;
    }
    
    public void removeRecipe(IItemStack output) {
        removeRecipe(new IItemStack[] {output}, new IFluidStack[0]);
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IItemStack[] itemOutputs, IFluidStack[] fluidOutputs) {
        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, itemOutputs, fluidOutputs));
    }
}
