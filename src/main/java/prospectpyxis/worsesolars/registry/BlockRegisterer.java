package prospectpyxis.worsesolars.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import prospectpyxis.worsesolars.ModSettings;
import prospectpyxis.worsesolars.WorseSolars;
import prospectpyxis.worsesolars.block.BlockInfiniteSolar;
import prospectpyxis.worsesolars.block.BlockWorseSolar;

public class BlockRegisterer {

    public static BlockWorseSolar worsesolar = new BlockWorseSolar();
    public static BlockInfiniteSolar infinitesolar = new BlockInfiniteSolar();

    @SuppressWarnings("deprecation")
    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                worsesolar
        );
        GameRegistry.registerTileEntity(worsesolar.getTileEntityClass(), worsesolar.getRegistryName().toString());

        if (ModSettings.infiniteSolars.enabled) {
            registry.register(infinitesolar);
            GameRegistry.registerTileEntity(infinitesolar.getTileEntityClass(), infinitesolar.getRegistryName().toString());
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(worsesolar.createItemBlock());

        if (ModSettings.infiniteSolars.enabled) registry.register(infinitesolar.createItemBlock());
    }

    public static void registerModels() {
        WorseSolars.proxy.registerItemRenderer(Item.getItemFromBlock(worsesolar), 0, "worse_solar_panel_normal");
        WorseSolars.proxy.registerItemRenderer(Item.getItemFromBlock(worsesolar), 2, "worse_solar_panel_decayed");

        WorseSolars.proxy.registerItemRenderer(Item.getItemFromBlock(infinitesolar), 0, "infinite_solar_panel");
    }
}
