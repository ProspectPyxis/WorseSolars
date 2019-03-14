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

public class ItemBlockWorseSolar extends ItemBlock {

    public ItemBlockWorseSolar(Block b) {
        super(b);
        setHasSubtypes(true);
        setUnlocalizedName(b.getRegistryName().toString());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getMetadata() == 2) {
            tooltip.add(I18n.format("tooltip.worse_solar_panel.decayed"));
            if (ModConfig.blockProperties.canRepair) {
                tooltip.add(I18n.format("tooltip.worse_solar_panel.decayed.canrepair"));
            }
        }

        if (stack.getMetadata() == 0) {
            tooltip.add(I18n.format("tooltip.worse_solar_panel.normal"));
            NBTTagCompound nbt = stack.getSubCompound("BlockEntityTag") == null ? new NBTTagCompound() : stack.getSubCompound("BlockEntityTag");
            float dt = nbt.hasNoTags() ? (float)ModConfig.blockProperties.panelDurability : (float)nbt.getInteger("decayTimer");
            int time = (int)Math.floor(dt / (20f * 60f));
            if (time >= 60) {
                int hours = (int)Math.floor((float)time / 60f);
                if (hours > 1) {
                    tooltip.add(I18n.format("tooltip.worse_solar_panel.timeleft.hours"));
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
}
