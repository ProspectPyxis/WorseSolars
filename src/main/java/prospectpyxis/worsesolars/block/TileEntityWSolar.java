package prospectpyxis.worsesolars.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import prospectpyxis.worsesolars.core.EnergyBase;
import prospectpyxis.worsesolars.registry.SoundRegisterer;

import javax.annotation.Nullable;

public class TileEntityWSolar extends TileEntity implements ITickable {

    private boolean canProducePower;
    private int decayTimer = 24000;
    private boolean hasDecayed = false;

    public EnergyBase eContainer = new EnergyBase(40000, 40);

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
            if (world.getTotalWorldTime() % 20 == 0) {
                canProducePower = world.canBlockSeeSky(pos.up()) && !world.isRaining() && !world.isThundering() && world.isDaytime();
            }

            if (world.getTileEntity(pos) instanceof TileEntityWSolar) {
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
            }

            if (!hasDecayed && canProducePower) {
                eContainer.obtainEnergy(2);
                decrementDecay();
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
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        decayTimer = compound.getInteger("decayTimer");
        hasDecayed = compound.getBoolean("hasDecayed");
        eContainer.setEnergy(compound.getInteger("energy"));

        super.readFromNBT(compound);
    }

    public void decrementDecay() {
        decayTimer--;
        markDirty();
    }
}
