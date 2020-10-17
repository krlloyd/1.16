package cofh.core.init;

import net.minecraft.entity.ai.attributes.RangedAttribute;

import static cofh.core.CoFHCore.ATTRIBUTES;
import static cofh.core.util.references.CoreIDs.*;

public class CoreAttributes {

    private CoreAttributes() {

    }

    public static void register() {

        ATTRIBUTES.register(ID_ATTR_FALL_DISTANCE, () -> new RangedAttribute("cofh.fall_distance", 0.0D, -128.0D, 128.0D));
        ATTRIBUTES.register(ID_ATTR_HAZARD_RESISTANCE, () -> new RangedAttribute("cofh.hazard_resistance", 0.0D, 0.0D, 1.0D));
        ATTRIBUTES.register(ID_ATTR_STING_RESISTANCE, () -> new RangedAttribute("cofh.sting_resistance", 0.0D, 0.0D, 1.0D));
    }

}
