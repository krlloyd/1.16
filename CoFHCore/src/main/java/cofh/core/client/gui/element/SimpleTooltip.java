package cofh.core.client.gui.element;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;
import java.util.List;

public class SimpleTooltip implements ITooltipFactory {

    protected final TranslationTextComponent tooltip;

    public SimpleTooltip(TranslationTextComponent tooltip) {

        this.tooltip = tooltip;
    }

    @Override
    public List<IFormattableTextComponent> create(ElementBase element, int mouseX, int mouseY) {

        if (element.visible()) {
            return Collections.singletonList(tooltip);
        }
        return Collections.emptyList();
    }

}
