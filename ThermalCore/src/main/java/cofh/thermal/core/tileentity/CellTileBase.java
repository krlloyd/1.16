package cofh.thermal.core.tileentity;

import cofh.core.tileentity.TileCoFH;
import cofh.core.util.control.IReconfigurableTile;
import cofh.core.util.control.ReconfigControlModule;
import cofh.core.util.helpers.MathHelper;
import cofh.thermal.core.util.control.CellReconfigControlModule;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.client.model.ModelDataManager;

import static cofh.core.util.constants.Constants.FACING_HORIZONTAL;
import static cofh.core.util.constants.NBTTags.*;
import static cofh.core.util.helpers.BlockHelper.*;

public abstract class CellTileBase extends ThermalTileBase implements IReconfigurableTile {

    protected int compareTracker;
    protected int levelTracker;
    protected int outputTracker;

    public int amountInput;
    public int amountOutput;

    protected ReconfigControlModule reconfigControl = new CellReconfigControlModule(this);

    public CellTileBase(TileEntityType<?> tileEntityTypeIn) {

        super(tileEntityTypeIn);

        compareTracker = 10;
    }

    @Override
    public TileCoFH worldContext(BlockState state, IBlockReader world) {

        reconfigControl.setFacing(state.get(FACING_HORIZONTAL));
        updateSidedHandlers();

        return this;
    }

    @Override
    public void updateContainingBlockInfo() {

        super.updateContainingBlockInfo();
        updateSideCache();
    }

    // region BASE PARAMETERS
    protected int getBaseEnergyStorage() {

        return 1000000;
    }

    protected int getBaseEnergyXfer() {

        return 1000;
    }
    // endregion

    // region HELPERS
    protected void updateSideCache() {

        Direction prevFacing = getFacing();
        Direction curFacing = getBlockState().get(FACING_HORIZONTAL);

        if (prevFacing != curFacing) {
            reconfigControl.setFacing(curFacing);

            int iPrev = prevFacing.getIndex();
            int iFace = curFacing.getIndex();
            SideConfig[] sides = new SideConfig[6];

            if (iPrev == SIDE_RIGHT[iFace]) {
                for (int i = 0; i < 6; ++i) {
                    sides[i] = reconfigControl.getSideConfig()[ROTATE_CLOCK_Y[i]];
                }
            } else if (iPrev == SIDE_LEFT[iFace]) {
                for (int i = 0; i < 6; ++i) {
                    sides[i] = reconfigControl.getSideConfig()[ROTATE_COUNTER_Y[i]];
                }
            } else if (iPrev == SIDE_OPPOSITE[iFace]) {
                for (int i = 0; i < 6; ++i) {
                    sides[i] = reconfigControl.getSideConfig()[INVERT_AROUND_Y[i]];
                }
            }
            reconfigControl.setSideConfig(sides);
        }
        updateSidedHandlers();
    }

    protected void updateTrackers(boolean send) {

    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {

        super.onDataPacket(net, pkt);

        ModelDataManager.requestModelDataRefresh(this);
    }

    @Override
    public PacketBuffer getConfigPacket(PacketBuffer buffer) {

        super.getConfigPacket(buffer);

        buffer.writeInt(amountInput);
        buffer.writeInt(amountOutput);

        return buffer;
    }

    @Override
    public PacketBuffer getControlPacket(PacketBuffer buffer) {

        super.getControlPacket(buffer);

        reconfigControl.writeToBuffer(buffer);

        return buffer;
    }

    @Override
    public PacketBuffer getGuiPacket(PacketBuffer buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(amountInput);
        buffer.writeInt(amountOutput);

        return buffer;
    }

    @Override
    public PacketBuffer getStatePacket(PacketBuffer buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(compareTracker);
        buffer.writeInt(levelTracker);

        return buffer;
    }

    @Override
    public void handleConfigPacket(PacketBuffer buffer) {

        super.handleConfigPacket(buffer);

        amountInput = MathHelper.clamp(buffer.readInt(), 0, energyStorage.getMaxReceive());
        amountOutput = MathHelper.clamp(buffer.readInt(), 0, energyStorage.getMaxExtract());
    }

    @Override
    public void handleControlPacket(PacketBuffer buffer) {

        super.handleControlPacket(buffer);

        reconfigControl.readFromBuffer(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }

    @Override
    public void handleGuiPacket(PacketBuffer buffer) {

        super.handleGuiPacket(buffer);

        amountInput = buffer.readInt();
        amountOutput = buffer.readInt();
    }

    @Override
    public void handleStatePacket(PacketBuffer buffer) {

        super.handleStatePacket(buffer);

        compareTracker = buffer.readInt();
        levelTracker = buffer.readInt();

        ModelDataManager.requestModelDataRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        reconfigControl.setFacing(Direction.byIndex(nbt.getByte(TAG_FACING)));
        reconfigControl.read(nbt);

        amountInput = nbt.getInt(TAG_AMOUNT_IN);
        amountOutput = nbt.getInt(TAG_AMOUNT_OUT);

        updateTrackers(false);

        updateSidedHandlers();
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putByte(TAG_FACING, (byte) reconfigControl.getFacing().getIndex());
        reconfigControl.write(nbt);

        nbt.putInt(TAG_AMOUNT_IN, amountInput);
        nbt.putInt(TAG_AMOUNT_OUT, amountOutput);

        return nbt;
    }
    // endregion

    // region AUGMENTS
    @Override
    protected void finalizeAttributes() {

        super.finalizeAttributes();

        amountInput = MathHelper.clamp(amountInput, 0, energyStorage.getMaxReceive());
        amountOutput = MathHelper.clamp(amountOutput, 0, energyStorage.getMaxExtract());
    }
    // endregion

    // region MODULES
    @Override
    public ReconfigControlModule reconfigControl() {

        return reconfigControl;
    }
    // endregion

    // region ITileCallback
    @Override
    public void onControlUpdate() {

        updateSidedHandlers();
        super.onControlUpdate();
    }
    // endregion

    // region CAPABILITIES
    protected void updateSidedHandlers() {

    }
    // endregion

    // region IConveyableData
    @Override
    public void readConveyableData(PlayerEntity player, CompoundNBT tag) {

        reconfigControl.read(tag);

        super.readConveyableData(player, tag);
    }

    @Override
    public void writeConveyableData(PlayerEntity player, CompoundNBT tag) {

        reconfigControl.write(tag);

        super.writeConveyableData(player, tag);
    }
    // endregion
}