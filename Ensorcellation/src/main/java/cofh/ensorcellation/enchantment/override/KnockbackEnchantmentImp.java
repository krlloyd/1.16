package cofh.ensorcellation.enchantment.override;

import cofh.core.enchantment.EnchantmentOverride;
import cofh.core.init.CoreEnchantmentTypes;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class KnockbackEnchantmentImp extends EnchantmentOverride {

    public KnockbackEnchantmentImp() {

        super(Rarity.UNCOMMON, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        maxLevel = 2;
    }

    @Override
    public int getMinEnchantability(int level) {

        return 5 + 20 * (level - 1);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {

        if (!enable) {
            return super.canApplyAtEnchantingTable(stack);
        }
        return CoreEnchantmentTypes.SWORD_OR_AXE.canEnchantItem(stack.getItem()) || supportsEnchantment(stack);
    }

}
