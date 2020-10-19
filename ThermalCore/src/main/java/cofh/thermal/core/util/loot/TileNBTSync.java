package cofh.thermal.core.util.loot;

import cofh.core.util.control.IReconfigurableTile;
import cofh.core.util.control.ISecurableTile;
import cofh.core.util.control.ITransferControllableTile;
import cofh.thermal.core.tileentity.ThermalTileBase;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import static cofh.core.util.constants.Constants.ID_THERMAL;
import static cofh.core.util.constants.NBTTags.TAG_AUGMENTS;
import static cofh.core.util.constants.NBTTags.TAG_BLOCK_ENTITY;
import static cofh.thermal.core.common.ThermalConfig.*;
import static net.minecraft.loot.LootParameters.BLOCK_ENTITY;

public class TileNBTSync extends LootFunction {

    private static LootFunctionType INSTANCE;

    public static void setup() {

        if (INSTANCE != null) {
            return;
        }
        INSTANCE = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(ID_THERMAL + ":nbt_sync"), new LootFunctionType(new Serializer()));
    }

    protected TileNBTSync(ILootCondition[] conditionsIn) {

        super(conditionsIn);
    }

    @Override
    public LootFunctionType getFunctionType() {

        return INSTANCE;
    }

    @Override
    public ItemStack doApply(ItemStack stack, LootContext context) {

        return applyToStack(stack, context.get(BLOCK_ENTITY));
    }

    public static ItemStack applyToStack(ItemStack stack, TileEntity tile) {

        if (tile instanceof ThermalTileBase) {
            ThermalTileBase castedTile = (ThermalTileBase) tile;

            CompoundNBT tag = new CompoundNBT();
            if (keepEnergy.get()) {
                castedTile.getEnergyStorage().write(tag);
            }
            if (keepItems.get()) {
                castedTile.getItemInv().writeSlotsToNBT(tag, 0, castedTile.invSize() - castedTile.augSize());
            }
            if (keepAugments.get() && castedTile.augSize() > 0) {
                castedTile.getItemInv().writeSlotsToNBTUnordered(tag, TAG_AUGMENTS, castedTile.invSize() - castedTile.augSize());
            }
            if (keepFluids.get()) {
                castedTile.getTankInv().write(tag);
            }
            if (keepRSControl.get()) {
                castedTile.redstoneControl().write(tag);
            }
            if (keepSideConfig.get() && tile instanceof IReconfigurableTile) {
                ((IReconfigurableTile) tile).reconfigControl().write(tag);
            }
            if (keepTransferControl.get() && tile instanceof ITransferControllableTile) {
                ((ITransferControllableTile) tile).transferControl().write(tag);
            }
            if (((ISecurableTile) tile).hasSecurity()) {
                castedTile.securityControl().write(tag);
            }
            if (!tag.isEmpty()) {
                stack.setTagInfo(TAG_BLOCK_ENTITY, tag);
            }
        }
        return stack;
    }

    public static LootFunction.Builder<?> builder() {

        return builder(TileNBTSync::new);
    }

    public static class Serializer extends LootFunction.Serializer<TileNBTSync> {

        public TileNBTSync deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {

            return new TileNBTSync(conditionsIn);
        }

    }

}
