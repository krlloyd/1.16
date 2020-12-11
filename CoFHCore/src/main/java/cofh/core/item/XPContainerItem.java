package cofh.core.item;

import cofh.core.util.Utils;
import cofh.core.util.helpers.FluidHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.core.util.constants.Constants.MB_PER_XP;
import static cofh.core.util.constants.Constants.RGB_DURABILITY_XP;
import static cofh.core.util.helpers.StringHelper.*;
import static cofh.core.util.helpers.XPHelper.*;
import static cofh.core.util.references.CoreReferences.FLUID_EXPERIENCE;
import static cofh.core.util.references.CoreReferences.HOLDING;

/**
 * This class does not set the XP Timer on the player entity.
 */
public class XPContainerItem extends FluidContainerItem implements IXPContainerItem {

    protected int xpCapacity;

    public XPContainerItem(Properties builder, int fluidCapacity) {

        super(builder, fluidCapacity, FluidHelper.IS_XP); // TODO: Validator w/ xp fluid tag
        xpCapacity = fluidCapacity / MB_PER_XP;
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.add(getTextComponent(localize("info.cofh.amount") + ": " + getScaledNumber(getStoredXP(stack)) + " / " + getScaledNumber(getCapacityXP(stack))));
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {

        return RGB_DURABILITY_XP;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItem(hand);
        if (Utils.isFakePlayer(player)) {
            return ActionResult.resultFail(stack);
        }
        int xp;
        int curLevel = player.experienceLevel;

        if (player.isSneaking()) {
            if (getExtraPlayerXP(player) > 0) {
                xp = Math.min(getTotalXPForLevel(player.experienceLevel + 1) - getTotalXPForLevel(player.experienceLevel) - getExtraPlayerXP(player), getStoredXP(stack));
            } else {
                xp = Math.min(getTotalXPForLevel(player.experienceLevel + 1) - getTotalXPForLevel(player.experienceLevel), getStoredXP(stack));
            }
            setPlayerXP(player, getPlayerXP(player) + xp);
            if (player.experienceLevel < curLevel + 1 && getPlayerXP(player) >= getTotalXPForLevel(curLevel + 1)) {
                setPlayerLevel(player, curLevel + 1);
            }
            modifyXP(stack, -xp);
        } else {
            if (getExtraPlayerXP(player) > 0) {
                xp = Math.min(getExtraPlayerXP(player), getSpaceXP(stack));
                setPlayerXP(player, getPlayerXP(player) - xp);
                if (player.experienceLevel < curLevel) {
                    setPlayerLevel(player, curLevel);
                }
                modifyXP(stack, xp);
            } else if (player.experienceLevel > 0) {
                xp = Math.min(getTotalXPForLevel(player.experienceLevel) - getTotalXPForLevel(player.experienceLevel - 1), getSpaceXP(stack));
                setPlayerXP(player, getPlayerXP(player) - xp);
                if (player.experienceLevel < curLevel - 1) {
                    setPlayerLevel(player, curLevel - 1);
                }
                modifyXP(stack, xp);
            }
        }
        return ActionResult.resultSuccess(stack);
    }

    // region IXpContainerItem
    public int getCapacityXP(ItemStack stack) {

        int holding = EnchantmentHelper.getEnchantmentLevel(HOLDING, stack);
        return Utils.getEnchantedCapacity(xpCapacity, holding);
    }
    // endregion

    // region IFluidContainerItem
    @Override
    public FluidStack getFluid(ItemStack container) {

        int xp = getStoredXP(container);
        return xp > 0 ? new FluidStack(FLUID_EXPERIENCE, xp * MB_PER_XP) : FluidStack.EMPTY;
    }

    @Override
    public int getCapacity(ItemStack container) {

        return getCapacityXP(container) * MB_PER_XP;
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, IFluidHandler.FluidAction action) {

        if (resource.isEmpty() || !isFluidValid(container, resource)) {
            return 0;
        }
        int xp = getStoredXP(container);
        int filled = Math.min(getCapacityXP(container) - xp, resource.getAmount() / MB_PER_XP);

        if (action.execute()) {
            modifyXP(container, filled);
        }
        return filled * MB_PER_XP;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, IFluidHandler.FluidAction action) {

        int xp = getStoredXP(container);
        if (xp <= 0) {
            return FluidStack.EMPTY;
        }
        int drained = Math.min(xp, maxDrain / MB_PER_XP);

        if (action.execute() && !isCreative(container)) {
            modifyXP(container, -drained);
        }
        return new FluidStack(FLUID_EXPERIENCE, drained * MB_PER_XP);
    }

    // endregion
}