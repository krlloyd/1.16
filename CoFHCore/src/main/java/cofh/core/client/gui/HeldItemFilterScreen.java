package cofh.core.client.gui;

import cofh.core.client.gui.element.ElementButton;
import cofh.core.client.gui.element.SimpleTooltip;
import cofh.core.inventory.container.HeldItemFilterContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import static cofh.core.util.helpers.GuiHelper.createSlot;
import static cofh.core.util.helpers.GuiHelper.generatePanelInfo;
import static cofh.lib.util.constants.Constants.ID_COFH_CORE;

public class HeldItemFilterScreen extends ContainerScreenCoFH<HeldItemFilterContainer> {

    public static final String TEX_PATH = ID_COFH_CORE + ":textures/gui/generic.png";
    public static final ResourceLocation TEXTURE = new ResourceLocation(TEX_PATH);

    public static final String TEX_DENY_LIST = ID_COFH_CORE + ":textures/gui/filters/filter_deny_list.png";
    public static final String TEX_ALLOW_LIST = ID_COFH_CORE + ":textures/gui/filters/filter_allow_list.png";
    public static final String TEX_IGNORE_NBT = ID_COFH_CORE + ":textures/gui/filters/filter_ignore_nbt.png";
    public static final String TEX_USE_NBT = ID_COFH_CORE + ":textures/gui/filters/filter_use_nbt.png";

    public HeldItemFilterScreen(HeldItemFilterContainer container, PlayerInventory inv, ITextComponent titleIn) {

        super(container, inv, titleIn);

        texture = TEXTURE;
        info = generatePanelInfo("info.cofh_core.item_filter");
    }

    @Override
    public void init() {

        super.init();

        for (int i = 0; i < container.getFilterSize(); ++i) {
            Slot slot = container.inventorySlots.get(i);
            addElement(createSlot(this, slot.xPos, slot.yPos));
        }

        addElement(new ElementButton(this, 124, 25) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                container.setAllowList(true);
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture(TEX_DENY_LIST, 36, 18)
                .setTooltipFactory(new SimpleTooltip(new TranslationTextComponent("info.cofh.filter.allowlist.0")))
                .setVisible(() -> !container.getAllowList()));

        addElement(new ElementButton(this, 124, 25) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                container.setAllowList(false);
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture(TEX_ALLOW_LIST, 36, 18)
                .setTooltipFactory(new SimpleTooltip(new TranslationTextComponent("info.cofh.filter.allowlist.1")))
                .setVisible(() -> container.getAllowList()));

        addElement(new ElementButton(this, 124, 45) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                container.setCheckNBT(true);
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture(TEX_IGNORE_NBT, 36, 18)
                .setTooltipFactory(new SimpleTooltip(new TranslationTextComponent("info.cofh.filter.checkNBT.0")))
                .setVisible(() -> !container.getCheckNBT()));

        addElement(new ElementButton(this, 124, 45) {

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {

                container.setCheckNBT(false);
                return true;
            }
        }
                .setSize(18, 18)
                .setTexture(TEX_USE_NBT, 36, 18)
                .setTooltipFactory(new SimpleTooltip(new TranslationTextComponent("info.cofh.filter.checkNBT.1")))
                .setVisible(() -> container.getCheckNBT()));
    }

}
