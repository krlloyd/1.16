package cofh.ensorcellation.enchantment;

import cofh.core.enchantment.DamageEnchantmentCoFH;
import cofh.core.init.CoreEnchantmentTypes;
import net.minecraft.inventory.EquipmentSlotType;

public class InstigatingEnchantment extends DamageEnchantmentCoFH {

    public InstigatingEnchantment() {

        super(Rarity.UNCOMMON, CoreEnchantmentTypes.SWORD_OR_AXE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        maxLevel = 1;
        treasure = true;
    }

    @Override
    public int getMinEnchantability(int level) {

        return level * 25;
    }

    @Override
    protected int maxDelegate(int level) {

        return getMinEnchantability(level) + 50;
    }

}
