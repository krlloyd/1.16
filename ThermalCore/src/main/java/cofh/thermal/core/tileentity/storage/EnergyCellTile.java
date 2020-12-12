package cofh.thermal.core.tileentity.storage;

import cofh.core.energy.EnergyStorageCoFH;
import cofh.core.util.control.ReconfigControlModule;
import cofh.core.util.helpers.MathHelper;
import cofh.thermal.core.inventory.container.storage.EnergyCellContainer;
import cofh.thermal.core.tileentity.CellTileBase;
import cofh.thermal.core.util.control.CellReconfigControlModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.ITickableTileEntity;

import javax.annotation.Nullable;

import static cofh.thermal.core.common.ThermalConfig.storageAugments;
import static cofh.thermal.core.init.TCoreReferences.ENERGY_CELL_TILE;

public class EnergyCellTile extends CellTileBase implements ITickableTileEntity {

    public int amountInput;
    public int amountOutput;

    protected ReconfigControlModule reconfigControl = new CellReconfigControlModule(this);

    public EnergyCellTile() {

        super(ENERGY_CELL_TILE);

        energyStorage = new EnergyStorageCoFH(getBaseEnergyStorage(), getBaseEnergyXfer());

        amountInput = energyStorage.getMaxReceive();
        amountOutput = energyStorage.getMaxExtract();

        addAugmentSlots(storageAugments);
        initHandlers();
    }

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

    // region AUGMENTS
    @Override
    protected void finalizeAttributes() {

        super.finalizeAttributes();

        amountInput = MathHelper.clamp(amountInput, 0, energyStorage.getMaxReceive());
        amountOutput = MathHelper.clamp(amountOutput, 0, energyStorage.getMaxExtract());
    }
    // endregion
}