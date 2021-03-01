package cofh.lib.util.filter;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFilterableTile {

    IFilter getFilter();

    void onFilterChanged();

    BlockPos pos();

    World world();

}
