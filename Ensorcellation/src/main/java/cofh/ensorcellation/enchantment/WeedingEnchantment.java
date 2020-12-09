package cofh.ensorcellation.enchantment;

import cofh.core.enchantment.EnchantmentCoFH;
import cofh.core.init.CoreEnchantmentTypes;
import net.minecraft.inventory.EquipmentSlotType;

public class WeedingEnchantment extends EnchantmentCoFH {

    public WeedingEnchantment() {

        super(Rarity.UNCOMMON, CoreEnchantmentTypes.HOE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    protected int maxDelegate(int level) {

        return getMinEnchantability(level) + 50;
    }

}