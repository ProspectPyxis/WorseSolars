package prospectpyxis.worsesolars.item;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockInfiniteSolar extends ItemBlock {

    public ItemBlockInfiniteSolar(Block b) {
        super(b);
        setUnlocalizedName(b.getRegistryName().toString());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.infinite_solar_panel.normal"));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile.worse_solars.infinite_solar_panel";
    }
}
