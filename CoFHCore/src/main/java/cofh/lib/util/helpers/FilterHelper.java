package cofh.lib.util.helpers;

import net.minecraft.item.ItemStack;

import static cofh.lib.util.constants.NBTTags.TAG_FILTER_TYPE;
import static cofh.lib.util.helpers.AugmentableHelper.getPropertyWithDefault;

public class FilterHelper {

    private FilterHelper() {

    }

    public static boolean hasFilter(ItemStack stack) {

        return !getFilterType(stack).isEmpty();
    }

    public static String getFilterType(ItemStack stack) {

        return getPropertyWithDefault(stack, TAG_FILTER_TYPE, "");
    }

}

