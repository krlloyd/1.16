package cofh.core.util.filter;

import cofh.core.inventory.container.TileItemFilterContainer;
import cofh.lib.util.filter.IFilter;
import cofh.lib.util.filter.IFilterFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

import javax.annotation.Nullable;

public class TileItemFilter extends AbstractItemFilter {

    public static final IFilterFactory<IFilter> FACTORY = nbt -> new TileItemFilter(SIZE).read(nbt);

    public TileItemFilter(int size) {

        super(size);
    }

    // region INamedContainerProvider
    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new TileItemFilterContainer(i, inventory, player);
    }
    // endregion
}
