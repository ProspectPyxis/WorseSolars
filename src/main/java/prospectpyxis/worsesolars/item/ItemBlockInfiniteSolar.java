package prospectpyxis.worsesolars.item;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import prospectpyxis.worsesolars.ModConfig;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockInfiniteSolar extends ItemBlock {

    public ItemBlockInfiniteSolar(Block b) {
        super(b);
        setUnlocalizedName(b.getRegistryName().toString());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.infinite_solar_panel.normal"));
    }
}