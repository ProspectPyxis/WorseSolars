package prospectpyxis.worsesolars.block;

import net.minecraft.block.Block;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import prospectpyxis.pyxislib.block.BlockWithTileEntity;
import prospectpyxis.worsesolars.ModSettings;
import prospectpyxis.worsesolars.block.tile.TileEntityWorseSolar;
import prospectpyxis.worsesolars.item.ItemBlockWorseSolar;

import javax.annotation.Nullable;

public class BlockWorseSolar extends BlockWithTileEntity<TileEntityWorseSolar> {

    // 0 is inactive, 1 is active, 2 is decayed
    public static final PropertyInteger STATUS = PropertyInteger.create("status", 0, 2);

    public BlockWorseSolar() {
        super(Material.IRON);

        setRegistryName("worse_solar_panel");
        setUnlocalizedName("worse_solar_panel");
        setHardness(10.0f);
        setResistance(24.0f);

        this.setDefaultState(this.getBlockState().getBaseState().withProperty(STATUS, 0));

        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntityWorseSolar tews = world.getTileEntity(pos) instanceof TileEntityWorseSolar ? (TileEntityWorseSolar)world.getTileEntity(pos) : null;
        if (tews != null) {
            int meta = state.getBlock().getMetaFromState(state) == 1 ? 0 : state.getBlock().getMetaFromState(state);
            ItemStack item = new ItemStack(this, 1, meta);
            NBTTagCompound data = new NBTTagCompound();
            tews.writeToNBT(data);
            if (!ModSettings.blockProperties.keepEnergy) data.removeTag("energy");
            data.removeTag("isPowered");
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

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!ModSettings.blockProperties.redstoneControl) return;
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityWorseSolar) {
            TileEntityWorseSolar tews = (TileEntityWorseSolar)te;
            tews.updateSignal();
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true;
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool)
    {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityWorseSolar) {
            TileEntityWorseSolar tews = (TileEntityWorseSolar)te;
            return tews.getComparatorOutput();
        }
        return 0;
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
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));

        NBTTagCompound nbt1 = new NBTTagCompound();
        NBTTagCompound nbt2 = new NBTTagCompound();
        nbt1.setInteger("decayTimer", 0);
        nbt1.setBoolean("hasDecayed", true);
        nbt2.setTag("BlockEntityTag", nbt1);

        ItemStack subitm = new ItemStack(this, 1, 2);
        subitm.setTagCompound(nbt2);

        items.add(subitm);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return ModSettings.blockProperties.redstoneControl;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, world.getBlockState(pos).getValue(STATUS) == 1 ? 0 : world.getBlockState(pos).getValue(STATUS));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(STATUS) == 1 ? 0 : state.getValue(STATUS);
    }

    @Override
    public Class<TileEntityWorseSolar> getTileEntityClass() {
        return TileEntityWorseSolar.class;
    }

    @Nullable
    @Override
    public TileEntityWorseSolar createTileEntity(World world, IBlockState state) {
        return new TileEntityWorseSolar();
    }

    public Item createItemBlock() {
        return new ItemBlockWorseSolar(this).setRegistryName(getRegistryName());
    }
}
