package cofh.core.item;

import cofh.core.util.Utils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import static cofh.core.util.references.CoreReferences.HOLDING;

/**
 * Marker interface for anything that supports the "Holding" enchantment. Can also be done via the Enchantable capability, but this is way less overhead.
 */
public interface IContainerItem extends ICoFHItem {

    default int getMaxStored(ItemStack container, int amount) {

        int holding = EnchantmentHelper.getEnchantmentLevel(HOLDING, container);
        return Utils.getEnchantedCapacity(amount, holding);
    }

}
