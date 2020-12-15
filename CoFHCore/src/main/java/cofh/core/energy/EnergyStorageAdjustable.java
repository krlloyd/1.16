package cofh.core.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.function.IntSupplier;

import static cofh.core.util.constants.NBTTags.*;

/**
 * Implementation of an Energy Storage object. See {@link IEnergyStorage}.
 * Additional constraints (receive/extract) are provided.
 *
 * @author King Lemming
 */
public class EnergyStorageAdjustable extends EnergyStorageCoFH {

    protected IntSupplier curReceive = this::getMaxReceive;
    protected IntSupplier curExtract = this::getMaxExtract;

    public EnergyStorageAdjustable(int capacity) {

        this(capacity, capacity, capacity, 0);
    }

    public EnergyStorageAdjustable(int capacity, int maxTransfer) {

        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyStorageAdjustable(int capacity, int maxReceive, int maxExtract) {

        this(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyStorageAdjustable(int capacity, int maxReceive, int maxExtract, int energy) {

        super(capacity, maxReceive, maxExtract, energy);
    }

    public EnergyStorageAdjustable setTransferLimits(IntSupplier curReceive, IntSupplier curExtract) {

        this.curReceive = curReceive;
        this.curExtract = curExtract;
        return this;
    }

    // region NBT
    public EnergyStorageCoFH read(CompoundNBT nbt) {

        this.energy = nbt.getInt(TAG_ENERGY);
        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public CompoundNBT write(CompoundNBT nbt) {

        if (this.capacity <= 0) {
            return nbt;
        }
        nbt.putInt(TAG_ENERGY, energy);
        nbt.putInt(TAG_ENERGY_MAX, baseCapacity);
        nbt.putInt(TAG_ENERGY_RECV, this.curReceive.getAsInt());
        nbt.putInt(TAG_ENERGY_SEND, this.curExtract.getAsInt());
        return nbt;
    }
    // endregion

    // region IEnergyStorage
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {

        if (maxReceive > this.curReceive.getAsInt()) {
            return super.receiveEnergy(this.curReceive.getAsInt(), simulate);
        }
        return super.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        if (maxExtract > this.curExtract.getAsInt()) {
            return super.extractEnergy(this.curExtract.getAsInt(), simulate);
        }
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public boolean canExtract() {

        return curExtract.getAsInt() > 0;
    }

    @Override
    public boolean canReceive() {

        return curReceive.getAsInt() > 0;
    }
    // endregion
}
