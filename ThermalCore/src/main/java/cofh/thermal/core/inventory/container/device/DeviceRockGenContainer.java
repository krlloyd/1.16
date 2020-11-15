package cofh.thermal.core.inventory.container.device;

import cofh.core.inventory.InvWrapperCoFH;
import cofh.core.inventory.container.TileContainer;
import cofh.core.inventory.container.slot.SlotRemoveOnly;
import cofh.thermal.core.tileentity.ThermalTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static cofh.core.util.helpers.ItemHelper.cloneStack;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_ROCK_GEN_CONTAINER;

public class DeviceRockGenContainer extends TileContainer {

    public final ThermalTileBase tile;

    public DeviceRockGenContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(DEVICE_ROCK_GEN_CONTAINER, windowId, world, pos, inventory, player);
        this.tile = (ThermalTileBase) world.getTileEntity(pos);
        InvWrapperCoFH tileInv = new InvWrapperCoFH(this.tile.getItemInv());

        addSlot(new SlotRemoveOnly(tileInv, 0, 44, 35) {

            @Override
            public void putStack(ItemStack stack) {

            }

            @Override
            public ItemStack decrStackSize(int amount) {

                return cloneStack(this.getStack());
            }
        });

        bindAugmentSlots(tileInv, 1, this.tile.augSize());
        bindPlayerInventory(inventory);
    }

    // region OVERRIDES
    @Override
    protected boolean performMerge(int index, ItemStack stack) {

        // TODO: Consider reverting or allowing augment shift-click in some cases.
        // int invBase = getSizeTileInventory();
        int invBase = getSizeTileInventory() - getNumAugmentSlots();
        int invFull = inventorySlots.size();
        int invHotbar = invFull - 9;
        int invPlayer = invHotbar - 27;

        if (index == 0) {   // If the output slot, only transfer a COPY.
            stack = cloneStack(stack);
        }
        if (index < invPlayer) {
            return mergeItemStack(stack, invPlayer, invFull, false);
        } else {
            return mergeItemStack(stack, 1, invBase, false);
        }
    }
    // endregion
}
