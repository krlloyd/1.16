package cofh.ensorcellation.enchantment;

import cofh.core.enchantment.EnchantmentCoFH;
import cofh.core.init.CoreEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

import static cofh.core.util.references.EnsorcReferences.FURROWING;

public class TillingEnchantment extends EnchantmentCoFH {

    public TillingEnchantment() {

        super(Rarity.RARE, CoreEnchantments.Types.HOE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        maxLevel = 4;
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

    @Override
    public boolean canApplyTogether(Enchantment ench) {

        return super.canApplyTogether(ench) && ench != FURROWING;
    }

}