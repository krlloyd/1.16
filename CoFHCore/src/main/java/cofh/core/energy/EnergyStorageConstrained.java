package cofh.core.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.function.IntSupplier;

import static cofh.core.util.constants.NBTTags.*;

/**
 * Implementation of an Energy Storage object. See {@link IEnergyStorage}.
 *
 * @author King Lemming
 */
public class EnergyStorageConstrained extends EnergyStorageCoFH {

    protected IntSupplier curReceive = this::getMaxReceive;
    protected IntSupplier curExtract = this::getMaxExtract;

    public EnergyStorageConstrained(int capacity) {

        this(capacity, capacity, capacity, 0);
    }

    public EnergyStorageConstrained(int capacity, int maxTransfer) {

        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyStorageConstrained(int capacity, int maxReceive, int maxExtract) {

        this(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyStorageConstrained(int capacity, int maxReceive, int maxExtract, int energy) {

        super(capacity, maxReceive, maxExtract, energy);
    }

    public EnergyStorageConstrained setConstraints(IntSupplier curReceive, IntSupplier curExtract) {

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

        int energyReceived = Math.min(capacity - energy, Math.min(curReceive.getAsInt(), maxReceive));
        if (!simulate) {
            energy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        int energyExtracted = Math.min(energy, Math.min(curExtract.getAsInt(), maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
        }
        return energyExtracted;
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
