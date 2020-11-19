package cofh.thermal.expansion.plugins.crt.machine;

import cofh.thermal.expansion.init.TExpRecipeTypes;
import cofh.thermal.expansion.plugins.crt.actions.ActionRemoveThermalRecipeByOutput;
import cofh.thermal.expansion.util.recipes.machine.BottlerRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.thermal.Bottler")
public class CRTBottlerManager implements IRecipeManager {
    
    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient ingredient, IFluidStack fluidInput, int energy) {
        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        
        float experience = -1;
        int minTicks = -1;
        List<Ingredient> inputItems = Collections.singletonList(ingredient.asVanillaIngredient());
        List<FluidStack> inputFluids = Collections.singletonList(fluidInput.getInternal());
        List<ItemStack> outputItems = Collections.singletonList(output.getInternal());
        List<Float> outputItemChances = new ArrayList<>();
        List<FluidStack> outputFluids = new ArrayList<>();
        BottlerRecipe recipe = new BottlerRecipe(resourceLocation, energy, experience, minTicks, inputItems, inputFluids, outputItems, outputItemChances, outputFluids);
        
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe, ""));
    }
    
    @Override
    public IRecipeType<BottlerRecipe> getRecipeType() {
        return TExpRecipeTypes.RECIPE_BOTTLER;
    }
    
    public void removeRecipe(IItemStack output) {
        removeRecipe(new IItemStack[] {output});
    }
    
    @ZenCodeType.Method
    public void removeRecipe(IItemStack... output) {
        CraftTweakerAPI.apply(new ActionRemoveThermalRecipeByOutput(this, output));
    }
    
    
}
