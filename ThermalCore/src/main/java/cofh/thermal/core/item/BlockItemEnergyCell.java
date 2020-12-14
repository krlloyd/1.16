package cofh.thermal.core.item;

import cofh.core.energy.IEnergyContainerItem;
import cofh.core.item.BlockItemAugmentable;
import cofh.core.util.helpers.AugmentDataHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.constants.NBTTags.*;
import static cofh.core.util.helpers.AugmentableHelper.getAttributeFromAugmentMax;
import static cofh.core.util.helpers.AugmentableHelper.getPropertyWithDefault;
import static cofh.core.util.helpers.StringHelper.*;

public class BlockItemEnergyCell extends BlockItemAugmentable implements IEnergyContainerItem {

    public BlockItemEnergyCell(Block blockIn, Properties builder) {

        super(blockIn, builder);

        setEnchantability(5);
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        boolean creative = isCreative(stack);
        if (getMaxEnergyStored(stack) > 0) {
            tooltip.add(creative
                    ? getTextComponent("info.cofh.infinite_source")
                    : getTextComponent(localize("info.cofh.energy") + ": " + getScaledNumber(getEnergyStored(stack)) + " / " + getScaledNumber(getMaxEnergyStored(stack)) + " RF"));
        }
        // TODO: Determine if cell should work in inventory.

        //        int extract = getExtract(stack);
        //        int receive = getReceive(stack);
        //
        //        if (extract == receive && extract > 0 || creative) {
        //            tooltip.add(getTextComponent(localize("info.cofh.transfer") + ": " + getScaledNumber(extract) + " RF/t"));
        //        } else {
        //            tooltip.add(getTextComponent(localize("info.cofh.send") + "|" + localize("info.cofh.receive") + ": " + getScaledNumber(extract) + "|" + getScaledNumber(receive) + " RF/t"));
        //        }
    }

    // TODO: Determine if cell should work in inventory.

    //    @Override
    //    public boolean showDurabilityBar(ItemStack stack) {
    //
    //        return !isCreative(stack) && getEnergyStored(stack) > 0;
    //    }
    //
    //    @Override
    //    public int getRGBDurabilityForDisplay(ItemStack stack) {
    //
    //        return RGB_DURABILITY_FLUX;
    //    }
    //
    //    @Override
    //    public double getDurabilityForDisplay(ItemStack stack) {
    //
    //        if (stack.getTag() == null) {
    //            return 0;
    //        }
    //        return MathHelper.clamp(1.0D - getEnergyStored(stack) / (double) getMaxEnergyStored(stack), 0.0D, 1.0D);
    //    }
    //
    //    @Override
    //    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
    //
    //        return new EnergyContainerItemWrapper(stack, this);
    //    }

    protected void setAttributesFromAugment(ItemStack container, CompoundNBT augmentData) {

        CompoundNBT subTag = container.getChildTag(TAG_PROPERTIES);
        if (subTag == null) {
            return;
        }
        getAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_BASE_MOD);
        getAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_ENERGY_STORAGE);
        getAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_ENERGY_XFER);
    }

    // region IAugmentableItem
    @Override
    public void updateAugmentState(ItemStack container, List<ItemStack> augments) {

        container.getOrCreateTag().put(TAG_PROPERTIES, new CompoundNBT());
        for (ItemStack augment : augments) {
            CompoundNBT augmentData = AugmentDataHelper.getAugmentData(augment);
            if (augmentData == null) {
                continue;
            }
            setAttributesFromAugment(container, augmentData);
        }
        int energyExcess = getEnergyStored(container) - getMaxEnergyStored(container);
        if (energyExcess > 0) {
            setEnergyStored(container, getMaxEnergyStored(container));
        }
    }
    // endregion

    // region IEnergyContainerItem
    public ItemStack setDefaultTag(ItemStack stack, int energy) {

        stack.getOrCreateChildTag(TAG_BLOCK_ENTITY).putInt(TAG_ENERGY, energy);
        return stack;
    }

    public CompoundNBT getEnergyTag(ItemStack container) {

        return container.getChildTag(TAG_BLOCK_ENTITY);
    }

    @Override
    public int getExtract(ItemStack container) {

        return 0;
        //        CompoundNBT tag = getEnergyTag(container);
        //        if (tag == null) {
        //            return 0;
        //        }
        //        return tag.getInt(TAG_ENERGY_SEND);
    }

    @Override
    public int getReceive(ItemStack container) {

        return 0;
        //        CompoundNBT tag = getEnergyTag(container);
        //        if (tag == null) {
        //            return 0;
        //        }
        //        return tag.getInt(TAG_ENERGY_RECV);
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {

        CompoundNBT tag = getEnergyTag(container);
        if (tag == null) {
            return 0;
        }
        float base = getPropertyWithDefault(container, TAG_AUGMENT_BASE_MOD, 1.0F);
        float mod = getPropertyWithDefault(container, TAG_AUGMENT_ENERGY_STORAGE, 1.0F);
        return getMaxStored(container, Math.round(tag.getInt(TAG_ENERGY_MAX) * mod * base));
    }
    // endregion
}
