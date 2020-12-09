package cofh.core.init;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.*;

public class CoreEnchantmentTypes {

    private CoreEnchantmentTypes() {

    }

    public static void register() {

        HOE = EnchantmentType.create("HOE", (item -> item instanceof HoeItem));
        PICKAXE_OR_SHOVEL = EnchantmentType.create("PICKAXE_OR_SHOVEL", (item -> item instanceof PickaxeItem || item instanceof ShovelItem));
        SWORD_OR_AXE = EnchantmentType.create("SWORD_OR_AXE", (item -> item instanceof SwordItem || item instanceof AxeItem));
        SWORD_OR_AXE_OR_CROSSBOW = EnchantmentType.create("SWORD_OR_AXE_OR_CROSSBOW", (item -> item instanceof SwordItem || item instanceof AxeItem || item instanceof CrossbowItem));
    }

    public static EnchantmentType HOE;
    public static EnchantmentType PICKAXE_OR_SHOVEL;
    public static EnchantmentType SWORD_OR_AXE;
    public static EnchantmentType SWORD_OR_AXE_OR_CROSSBOW;

}
