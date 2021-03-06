package prospectpyxis.worsesolars.item;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import prospectpyxis.worsesolars.ModSettings;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockWorseSolar extends ItemBlock {

    public ItemBlockWorseSolar(Block b) {
        super(b);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getMetadata() == 2) {
            tooltip.add(I18n.format("tooltip.worse_solar_panel.decayed"));
            if (ModSettings.blockProperties.canRepair) {
                tooltip.add(I18n.format("tooltip.worse_solar_panel.decayed.canrepair"));
            }
        }

        if (stack.getMetadata() == 0) {
            tooltip.add(I18n.format("tooltip.worse_solar_panel.normal"));
            NBTTagCompound nbt = stack.getSubCompound("BlockEntityTag") == null ? new NBTTagCompound() : stack.getSubCompound("BlockEntityTag");
            float dt = nbt.hasNoTags() ? (float) ModSettings.blockProperties.panelDurability : (float)nbt.getInteger("decayTimer");
            int time = (int)Math.floor(dt / (20f * 60f));
            if (time >= 60) {
                int hours = (int)Math.floor((float)time / 60f);
                if (hours > 1) {
                    tooltip.add(I18n.format("tooltip.worse_solar_panel.timeleft.hours", Integer.toString(hours)));
                }
                else {
                    tooltip.add(I18n.format("tooltip.worse_solar_panel.timeleft.onehour"));
                }
            }
            else if (time > 1) {
                tooltip.add(I18n.format("tooltip.worse_solar_panel.timeleft.normal", Integer.toString(time)));
            }
            else if (time == 1) {
                tooltip.add(I18n.format("tooltip.worse_solar_panel.timeleft.onemin"));
            }
            else {
                tooltip.add(I18n.format("tooltip.worse_solar_panel.timeleft.zeromin"));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta == 2) {
            return "tile.worse_solars.worse_solar_panel.decayed";
        } else {
            return "tile.worse_solars.worse_solar_panel";
        }
    }
}
