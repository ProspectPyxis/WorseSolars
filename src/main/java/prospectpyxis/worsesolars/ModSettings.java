package prospectpyxis.worsesolars;

import net.minecraftforge.common.config.Config;

@Config(modid = WorseSolars.modid)
public class ModSettings {

    public static final BlockProperties blockProperties = new BlockProperties();
    public static final InfiniteSolars infiniteSolars = new InfiniteSolars();

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
                "The chance per tick that the solar panel will lose durability. This is a \"1 out of x\" chance. [default: 1]"
        })
        @Config.RangeInt(min = 1)
        public int decayChance = 1;

        @Config.Comment({
                "Whether the solar panel will lose durability all the time or only when producing power.",
                "[default: true]"
        })
        public boolean panelConstantDrain = true;

        @Config.Comment({ "If true, the solar panel can be disabled with a redstone signal. [default: false]" })
        public boolean redstoneControl = false;

        @Config.Comment({
                "Controls how fast the solar panel decays when disabled by redstone, in ticks per durability point.",
                "Note that this does nothing if redstoneControl is set to false.",
                "Set to 0 to completely disable loss when redstone powered. [default: 5]"
        })
        @Config.RangeInt(min = 0)
        public int poweredOffDecayRate = 5;

        @Config.Comment({ "Whether the solar panel keeps energy when broken. [default: false]" })
        public boolean keepEnergy = false;

        @Config.Comment({
                "Disabling this will disallow the solar panel from giving a comparator output.",
                "[default: true]"
        })
        public boolean canComparatorOutput = true;

        @Config.Comment({ "Disabling this will disallow repairing broken solar panels. [default: true]" })
        public boolean canRepair = true;
    }

    public static class InfiniteSolars {

        @Config.Comment({ "Whether the infinite solar panel is enabled. [default: false]" })
        public boolean enabled = false;

        @Config.Comment({ "How much FE/t the solar panel produces. [default: 4]" })
        public int FEperTick = 4;

        @Config.Comment({ "How much FE the solar panel can contain. [default: 80000]" })
        public int energyCapacity = 80000;

        @Config.Comment({ "How much FE it can output to other blocks per tick. [default: 80]" })
        public int transferRate = 80;

        @Config.Comment({ "Whether the solar panel keeps energy when broken. [default: false]" })
        public boolean keepEnergy = false;
    }
}
