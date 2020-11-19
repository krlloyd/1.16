package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.core.util.recipes.ThermalRecipe;
import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.expansion.util.recipes.machine.PulverizerRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
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
@ZenCodeType.Name("mods.thermal.Pulverizer")
public class CRTPulverizerManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addRecipe(String name, MCWeightedItemStack[] outputs, IIngredient ingredient, float experience, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        int minTicks = -1;
        List<Ingredient> inputItems = Collections.singletonList(ingredient.asVanillaIngredient());
        List<FluidStack> inputFluids = new ArrayList<>();
        List<ItemStack> outputItems = Arrays.stream(outputs).map(MCWeightedItemStack::getItemStack).map(IItemStack::getInternal).collect(Collectors.toList());
        List<Float> outputItemChances = Arrays.stream(outputs).map(mcWeightedItemStack -> (float) mcWeightedItemStack.getWeight()).collect(Collectors.toList());
        List<FluidStack> outputFluids = new ArrayList<>();
        
        PulverizerRecipe recipe = new PulverizerRecipe(resourceLocation, energy, experience, minTicks, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
        
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public IRecipeType<PulverizerRecipe> getRecipeType() {
        return TExpRecipeTypes.RECIPE_PULVERIZER;
    }
    
    @Override
    public void removeRecipe(IItemStack output) {
        removeRecipe(new IItemStack[] {output});
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IItemStack... output) {
        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, output));
    }
}
