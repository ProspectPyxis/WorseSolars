package prospectpyxis.worsesolars.block.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import prospectpyxis.pyxislib.capability.energy.EnergyGenerator;
import prospectpyxis.worsesolars.ModSettings;
import prospectpyxis.worsesolars.block.BlockWorseSolar;
import prospectpyxis.worsesolars.registry.SoundRegisterer;

import javax.annotation.Nullable;

public class TileEntityWorseSolar extends TileEntity implements ITickable {

    private boolean canProducePower;
    private int decayTimer = ModSettings.blockProperties.panelDurability;
    private boolean hasDecayed = false;
    private boolean alreadyUpdated = false;
    private boolean isRedstonePowered = false;

    public EnergyGenerator eContainer = new EnergyGenerator(ModSettings.blockProperties.energyCapacity, ModSettings.blockProperties.transferRate);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T)eContainer;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            // Check if it can produce power once a second
            if (world.getTotalWorldTime() % 20 == 0) {
                if (!alreadyUpdated) alreadyUpdated = true;
                if (!hasDecayed) {
                    if (!isRedstonePowered) canProducePower = (world.canBlockSeeSky(pos.up()) && world.isDaytime())
                            && ((!world.isRaining() && !world.isThundering()) || !world.getBiome(pos).canRain());
                    else canProducePower = false;
                    world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
                }
            }

            if (world.getTileEntity(pos) instanceof TileEntityWorseSolar) {
                if (!hasDecayed) {
                    if (canProducePower) {
                        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockWorseSolar.STATUS, 1), 3);
                    } else {
                        world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockWorseSolar.STATUS, 0), 3);
                    }
                } else {
                    world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockWorseSolar.STATUS, 2), 3);
                }
            }

            if (decayTimer <= 0 && !hasDecayed) {
                hasDecayed = true;
                world.playSound(null, pos, SoundRegisterer.shortout, SoundCategory.BLOCKS, 1f, 1f);
                world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
            }

            if (!hasDecayed && alreadyUpdated) {
                if (isRedstonePowered) {
                    if (ModSettings.blockProperties.poweredOffDecayRate != 0 && world.getTotalWorldTime() % ModSettings.blockProperties.poweredOffDecayRate == 0) {
                        decrementDecay();
                    }
                }
                else if (canProducePower) {
                    eContainer.generateEnergy(ModSettings.blockProperties.FEpertick);
                    decrementDecay();
                }
                else if (ModSettings.blockProperties.panelConstantDrain) {
                    decrementDecay();
                }
            }

            for (EnumFacing f : EnumFacing.values()) {
                TileEntity te = world.getTileEntity(pos.offset(f));
                if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, f.getOpposite())) {
                    IEnergyStorage oCap = te.getCapability(CapabilityEnergy.ENERGY, f.getOpposite());
                    int energySent = oCap.receiveEnergy(Math.min(eContainer.getEnergyStored(), ModSettings.blockProperties.transferRate), false);
                    eContainer.extractEnergy(energySent, false);
                }
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("decayTimer", decayTimer);
        compound.setBoolean("hasDecayed", hasDecayed);
        compound.setInteger("energy", eContainer.getEnergyStored());
        compound.setBoolean("isPowered", isRedstonePowered);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        decayTimer = compound.getInteger("decayTimer");
        hasDecayed = compound.getBoolean("hasDecayed");
        eContainer.setEnergy(compound.getInteger("energy"));
        isRedstonePowered = compound.getBoolean("isPowered") && ModSettings.blockProperties.redstoneControl;

        super.readFromNBT(compound);
    }

    public void decrementDecay() {
        if (world.rand.nextFloat() <= 1f / (float)ModSettings.blockProperties.decayChance) {
            decayTimer--;
            markDirty();
        }
    }

    public int getComparatorOutput() {
        if (!ModSettings.blockProperties.canComparatorOutput) return 0;
        if (hasDecayed) {
            return 0;
        }
        else if (canProducePower) {
            return 2;
        }
        else {
            return 1;
        }
    }

    public void updateSignal() {
        int signal = 0;
        for (EnumFacing f : EnumFacing.values()) {
            signal = Math.max(signal, world.getRedstonePower(pos, f));
        }
        if (signal == 0) {
            isRedstonePowered = false;
            canProducePower = (world.canBlockSeeSky(pos.up()) && world.isDaytime())
                    && ((!world.isRaining() && !world.isThundering()) || !world.getBiome(pos).canRain());
        } else {
            isRedstonePowered = true;
            canProducePower = false;
        }
        world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
    }
}
