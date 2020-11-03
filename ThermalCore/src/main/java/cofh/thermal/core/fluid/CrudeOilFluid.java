package cofh.thermal.core.fluid;

import cofh.core.fluid.FluidCoFH;
import cofh.thermal.core.common.ThermalItemGroups;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

import static cofh.thermal.core.ThermalCore.FLUIDS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreIDs.ID_FLUID_CRUDE_OIL;

public class CrudeOilFluid extends FluidCoFH {

    public static CrudeOilFluid create() {

        return new CrudeOilFluid(ID_FLUID_CRUDE_OIL, "thermal:block/fluids/crude_oil_still", "thermal:block/fluids/crude_oil_flow");
    }

    protected CrudeOilFluid(String key, String stillTexture, String flowTexture) {

        super(FLUIDS, key, FluidAttributes.builder(new ResourceLocation(stillTexture), new ResourceLocation(flowTexture)).density(850).viscosity(1400));

        bucket = ITEMS.register(bucket(key), () -> new BucketItem(stillFluid, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ThermalItemGroups.THERMAL_ITEMS)));
        properties.bucket(bucket);
    }

}
