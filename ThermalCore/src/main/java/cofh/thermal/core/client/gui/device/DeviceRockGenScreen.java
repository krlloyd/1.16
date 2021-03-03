package cofh.thermal.core.client.gui.device;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermal.core.client.gui.ThermalTileScreenBase;
import cofh.thermal.core.inventory.container.device.DeviceRockGenContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_THERMAL;

public class DeviceRockGenScreen extends ThermalTileScreenBase<DeviceRockGenContainer> {

    public static final String TEX_PATH = ID_THERMAL + ":textures/gui/devices/rock_gen.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public DeviceRockGenScreen(DeviceRockGenContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, container.tile, StringHelper.getTextComponent("block.thermal.device_rock_gen"));
        texture = TEXTURE;
        info = generatePanelInfo("info.thermal.device_rock_gen");
    }

    //    @Override
    //    public void init() {
    //
    //        super.init();
    //    }

}
