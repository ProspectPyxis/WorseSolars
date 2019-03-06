package prospectpyxis.worsesolars.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import prospectpyxis.worsesolars.ModConfig;
import prospectpyxis.worsesolars.core.EnergyBase;

import javax.annotation.Nullable;

public class TileEntityInfiniteSolar extends TileEntity implements ITickable {

    private boolean canProducePower;

    public EnergyBase eContainer = new EnergyBase(ModConfig.infiniteSolars.energyCapacity, ModConfig.infiniteSolars.transferRate);

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
                canProducePower = (world.canBlockSeeSky(pos.up()) && world.isDaytime())
                        && ((!world.isRaining() && !world.isThundering()) || !world.getBiome(pos).canRain());
            }

            if (world.getTileEntity(pos) instanceof TileEntityInfiniteSolar) {
                if (canProducePower) {
                    world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockInfiniteSolar.STATUS, 1), 3);
                } else {
                    world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockInfiniteSolar.STATUS, 0), 3);
                }
            }

            if (canProducePower) {
                eContainer.obtainEnergy(ModConfig.infiniteSolars.FEperTick);
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
        compound.setInteger("energy", eContainer.getEnergyStored());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        eContainer.setEnergy(compound.getInteger("energy"));

        super.readFromNBT(compound);
    }
}
