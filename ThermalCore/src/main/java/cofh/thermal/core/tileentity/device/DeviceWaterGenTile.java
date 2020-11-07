package cofh.thermal.core.tileentity.device;

import cofh.core.fluid.FluidStorageCoFH;
import cofh.core.fluid.FluidStorageInfinite;
import cofh.core.inventory.ItemStorageCoFH;
import cofh.core.network.packet.client.TileControlPacket;
import cofh.core.util.StorageGroup;
import cofh.core.util.helpers.FluidHelper;
import cofh.thermal.core.inventory.container.device.DeviceWaterGenContainer;
import cofh.thermal.core.tileentity.ThermalTileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static cofh.core.util.StorageGroup.INTERNAL;
import static cofh.core.util.StorageGroup.OUTPUT;
import static cofh.core.util.constants.Constants.BUCKET_VOLUME;
import static cofh.core.util.constants.Constants.TANK_SMALL;
import static cofh.thermal.core.common.ThermalConfig.deviceAugments;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_WATER_GEN_TILE;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceWaterGenTile extends ThermalTileBase implements ITickableTileEntity {

    protected ItemStorageCoFH fillSlot = new ItemStorageCoFH(1, FluidHelper::hasFluidHandlerCap);

    protected byte adjWaterSource;

    protected boolean valid;

    protected FluidStorageCoFH tank = new FluidStorageInfinite(TANK_SMALL, e -> false);

    public DeviceWaterGenTile() {

        super(DEVICE_WATER_GEN_TILE);

        inventory.addSlot(fillSlot, INTERNAL);

        tankInv.addTank(tank, OUTPUT);

        addAugmentSlots(deviceAugments);
        initHandlers();

        renderFluid = new FluidStack(Fluids.WATER, BUCKET_VOLUME);
    }

    protected void updateValidity() {

        if (world == null || !world.isAreaLoaded(pos, 1)) {
            return;
        }
        adjWaterSource = 0;
        valid = false;

        BlockPos[] cardinals = new BlockPos[]{
                pos.north(),
                pos.south(),
                pos.west(),
                pos.east(),
        };
        for (BlockPos adj : cardinals) {
            FluidState state = world.getFluidState(adj);
            if (state.getFluid().equals(Fluids.WATER)) {
                ++adjWaterSource;
            }
        }
        if (adjWaterSource > 1) {
            tank.setFluidStack(new FluidStack(Fluids.WATER, tank.getCapacity()));
            valid = true;
        } else {
            tank.clear();
            fluidCap.invalidate();
        }
    }

    protected void updateActiveState() {

        boolean curActive = isActive;
        isActive = redstoneControl().getState() && valid;
        if (curActive != isActive) {
            fluidCap.invalidate();
        }
        updateActiveState(curActive);
    }

    protected void fillFluid() {

        if (!fillSlot.isEmpty()) {
            fillSlot.getItemStack()
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
                    .ifPresent(c -> {
                        c.fill(new FluidStack(tank.getFluidStack(), tank.getStored()), EXECUTE);
                        fillSlot.setItemStack(c.getContainer());
                    });
        }
    }

    @Override
    public void tick() {

        if (isActive) {
            fillFluid();
        }
    }

    @Override
    public void neighborChanged(Block blockIn, BlockPos fromPos) {

        super.neighborChanged(blockIn, fromPos);
        updateValidity();
        updateActiveState();
    }

    @Override
    public void onPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        super.onPlacedBy(worldIn, pos, state, placer, stack);
        updateValidity();
        updateActiveState();
    }

    @Nonnull
    @Override
    public IModelData getModelData() {

        return new ModelDataMap.Builder()
                .withInitial(FLUID, renderFluid)
                .build();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceWaterGenContainer(i, world, pos, inventory, player);
    }

    // TODO: Must be added if *other* water types are supported.
    //    // region NETWORK
    //    @Override
    //    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    //
    //        super.onDataPacket(net, pkt);
    //
    //        ModelDataManager.requestModelDataRefresh(this);
    //    }
    //
    //    @Override
    //    public void handleControlPacket(PacketBuffer buffer) {
    //
    //        super.handleControlPacket(buffer);
    //
    //        ModelDataManager.requestModelDataRefresh(this);
    //    }
    //
    //    @Override
    //    public void handleStatePacket(PacketBuffer buffer) {
    //
    //        super.handleStatePacket(buffer);
    //
    //        ModelDataManager.requestModelDataRefresh(this);
    //    }
    //    // endregion

    // region ITileCallback
    @Override
    public void onControlUpdate() {

        updateActiveState();
        TileControlPacket.sendToClient(this);
    }
    // endregion

    // region CAPABILITIES
    @Override
    protected <T> LazyOptional<T> getFluidHandlerCapability(@Nullable Direction side) {

        if (!fluidCap.isPresent()) {
            fluidCap = LazyOptional.of(() -> isActive ? tankInv.getHandler(StorageGroup.ACCESSIBLE) : EmptyFluidHandler.INSTANCE);
        }
        return fluidCap.cast();
    }
    // endregion
}
