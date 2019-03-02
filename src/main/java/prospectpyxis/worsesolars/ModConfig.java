package prospectpyxis.worsesolars;

import net.minecraftforge.common.config.Config;

@Config(modid = WorseSolars.modid)
public class ModConfig {

    public static final BlockProperties blockProperties = new BlockProperties();

    public static class BlockProperties {

        @Config.Comment({ "How much FE/t the solar panel produces. [default: 2]" })
        public int FEpertick = 2;

        @Config.Comment({ "How much FE the solar panel can contain. [default: 40000]" })
        public int energyCapacity = 40000;

        @Config.Comment({ "How much FE it can output to other blocks per tick. [default: 40]" })
        public int transferRate = 40;

        @Config.Comment({
                "How long the solar panel will last, in ticks. (20 ticks = 1 second)",
                "[default: 24000]"
        })
        public int panelDurability = 24000;

        @Config.Comment({
                "Whether the solar panel will lose durability all the time or only when producing power.",
                "[default: true]"
        })
        public boolean panelConstantDrain = true;

        @Config.Comment({ "Disabling this will disallow repairing broken solar panels. [default: true]" })
        public boolean canRepair = true;
    }
}
