package cofh.core.client.gui.element;

import net.minecraft.util.text.IFormattableTextComponent;

import java.util.Collections;
import java.util.List;

public interface ITooltipFactory {

    List<IFormattableTextComponent> create(ElementBase element, int mouseX, int mouseY);

    ITooltipFactory EMPTY = (element, mouseX, mouseY) -> Collections.emptyList();

}