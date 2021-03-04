package cofh.thermal.core.client.gui;

import cofh.core.client.gui.ContainerScreenCoFH;
import cofh.core.client.gui.element.ElementXpStorage;
import cofh.core.client.gui.element.panel.PanelAugmentation;
import cofh.core.client.gui.element.panel.PanelRedstoneControl;
import cofh.core.client.gui.element.panel.PanelSecurity;
import cofh.lib.inventory.container.ContainerCoFH;
import cofh.lib.util.helpers.FilterHelper;
import cofh.lib.util.helpers.SecurityHelper;
import cofh.thermal.core.tileentity.ThermalTileBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import static cofh.core.util.helpers.GuiHelper.createDefaultXpStorage;
import static cofh.core.util.helpers.GuiHelper.setClaimable;

public class ThermalTileScreenBase<T extends ContainerCoFH> extends ContainerScreenCoFH<T> {

    protected ThermalTileBase tile;

    public ThermalTileScreenBase(T container, PlayerInventory inv, ThermalTileBase tile, ITextComponent titleIn) {

        super(container, inv, titleIn);
        this.tile = tile;
    }

    @Override
    public void init() {

        super.init();

        // TODO: Enchantment Panel
        // addPanel(new PanelEnchantment(this, "This block can be enchanted."));
        addPanel(new PanelSecurity(this, tile, SecurityHelper.getID(player)));

        if (container.getAugmentSlots().size() > 0) {
            addPanel(new PanelAugmentation(this, container::getNumAugmentSlots, container.getAugmentSlots()));
        }
        addPanel(new PanelRedstoneControl(this, tile));

        if (tile.getXpStorage() != null) {
            addElement(setClaimable((ElementXpStorage) createDefaultXpStorage(this, 152, 65, tile.getXpStorage()).setVisible(() -> tile.getXpStorage().getMaxXpStored() > 0), tile));
        }
        if (FilterHelper.hasFilter(tile)) {
            // TODO: Filter widget
        }
    }

}
