package cofh.thermal.expansion.plugins.crt.base;

import cofh.thermal.expansion.util.recipes.dynamo.*;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class CRTFuel {
    
    private final ResourceLocation name;
    private final int energy;
    private List<Ingredient> item;
    private List<FluidStack> fluid;
    
    public CRTFuel(ResourceLocation name, int energy) {
        this.name = name;
        this.energy = energy;
    }
    
    public CRTFuel item(IIngredient item) {
        this.item = Collections.singletonList(item.asVanillaIngredient());
        return this;
    }
    
    public CRTFuel fluid(IFluidStack fluid) {
        this.fluid = Collections.singletonList(fluid.getInternal());
        return this;
    }
    
    public CompressionFuel compression() {
        return new CompressionFuel(name, energy, item, fluid);
    }
    
    public LapidaryFuel lapidary() {
        return new LapidaryFuel(name, energy, item, fluid);
    }
    
    public MagmaticFuel magmatic() {
        return new MagmaticFuel(name, energy, item, fluid);
    }
    
    public NumismaticFuel numismatic() {
        return new NumismaticFuel(name, energy, item, fluid);
    }
    
    public StirlingFuel stirling() {
        return new StirlingFuel(name, energy, item, fluid);
    }
    
    
}
