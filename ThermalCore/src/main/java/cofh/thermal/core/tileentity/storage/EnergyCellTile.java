package cofh.thermal.core.tileentity.storage;

import cofh.core.energy.EnergyStorageCoFH;
import cofh.core.util.helpers.MathHelper;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.core.tileentity.ReconfigurableTile4Way;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;

import javax.annotation.Nullable;

import static cofh.core.util.constants.NBTTags.TAG_AMOUNT_IN;
import static cofh.core.util.constants.NBTTags.TAG_AMOUNT_OUT;
import static cofh.thermal.core.common.ThermalConfig.storageAugments;
import static cofh.thermal.core.init.TCoreReferences.ENERGY_CELL_TILE;

public class EnergyCellTile extends ReconfigurableTile4Way implements ITickableTileEntity {

    public int amountInput;
    public int amountOutput;

    public EnergyCellTile() {

        super(ENERGY_CELL_TILE);

        energyStorage = new EnergyStorageCoFH(getBaseEnergyStorage(), getBaseEnergyXfer());

        amountInput = energyStorage.getMaxReceive();
        amountOutput = energyStorage.getMaxExtract();

        addAugmentSlots(storageAugments);
        initHandlers();
    }

    // region BASE PARAMETERS
    protected int getBaseEnergyStorage() {

        return 1000000;
    }

    protected int getBaseEnergyXfer() {

        return 1000;
    }
    // endregion

    @Override
    public void tick() {

        if (redstoneControl.getState()) {

        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new EnergyCellContainer(i, world, pos, inventory, player);
    }

    // region NETWORK
    @Override
    public PacketBuffer getConfigPacket(PacketBuffer buffer) {

        super.getConfigPacket(buffer);

        buffer.writeInt(amountInput);
        buffer.writeInt(amountOutput);

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
    public void handleConfigPacket(PacketBuffer buffer) {

        super.handleConfigPacket(buffer);

        amountInput = MathHelper.clamp(buffer.readInt(), 0, energyStorage.getMaxReceive());
        amountOutput = MathHelper.clamp(buffer.readInt(), 0, energyStorage.getMaxExtract());
    }

    @Override
    public void handleGuiPacket(PacketBuffer buffer) {

        super.handleGuiPacket(buffer);

        amountInput = buffer.readInt();
        amountOutput = buffer.readInt();
    }
    // endregion

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        amountInput = nbt.getInt(TAG_AMOUNT_IN);
        amountOutput = nbt.getInt(TAG_AMOUNT_OUT);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

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
}