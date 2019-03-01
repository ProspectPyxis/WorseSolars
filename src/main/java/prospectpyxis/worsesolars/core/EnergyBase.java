package prospectpyxis.worsesolars.core;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyBase extends EnergyStorage {

    public EnergyBase(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    public int obtainEnergy(int maxReceive) {
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        energy += energyReceived;
        return energyReceived;
    }

    public void setEnergy(int value) {
        energy = Math.min(capacity, value);
    }
}
