package cofh.lib.util.filter;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFilterableTile extends INamedContainerProvider {

    IFilter getFilter();

    void onFilterChanged();

    boolean openGui(ServerPlayerEntity player);

    // ItemStack getGuiIcon();

    boolean openFilterGui(ServerPlayerEntity player);

    // ItemStack getFilterGuiIcon();

    BlockPos pos();

    World world();

}
