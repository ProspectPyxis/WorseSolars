package prospectpyxis.worsesolars.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import prospectpyxis.worsesolars.ModConfig;
import prospectpyxis.worsesolars.core.BlockTEBase;
import prospectpyxis.worsesolars.item.ItemBlockInfiniteSolar;
import prospectpyxis.worsesolars.item.ItemBlockWorseSolar;

import javax.annotation.Nullable;

public class BlockInfiniteSolar extends BlockTEBase<TileEntityInfiniteSolar> {

    // 0 is inactive, 1 is active
    public static final PropertyInteger STATUS = PropertyInteger.create("status", 0, 1);

    public BlockInfiniteSolar() {
        super(Material.IRON, "infinite_solar_panel");

        setHardness(10.0f);
        setResistance(24.0f);

        this.setDefaultState(this.getBlockState().getBaseState().withProperty(STATUS, 0));

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntityInfiniteSolar teis = world.getTileEntity(pos) instanceof TileEntityInfiniteSolar ? (TileEntityInfiniteSolar)world.getTileEntity(pos) : null;
        if (teis != null) {
            ItemStack item = new ItemStack(this, 1, 0);
            NBTTagCompound data = new NBTTagCompound();
            teis.writeToNBT(data);
            if (!ModConfig.infiniteSolars.keepEnergy) data.removeTag("energy");
            data.removeTag("x");
            data.removeTag("y");
            data.removeTag("z");
            data.removeTag("id");
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setTag("BlockEntityTag", data);
            item.setTagCompound(nbt);
            drops.add(item);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true;
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STATUS);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STATUS, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STATUS);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public Class<TileEntityInfiniteSolar> getTileEntityClass() {
        return TileEntityInfiniteSolar.class;
    }

    @Nullable
    @Override
    public TileEntityInfiniteSolar createTileEntity(World world, IBlockState state) {
        return new TileEntityInfiniteSolar();
    }

    @Override
    public Item createItemBlock() {
        return new ItemBlockInfiniteSolar(this).setRegistryName(getRegistryName());
    }
}
