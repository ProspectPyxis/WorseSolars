package prospectpyxis.worsesolars.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import prospectpyxis.worsesolars.ModConfig;
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

        if (ModConfig.infiniteSolars.enabled) {
            registry.register(infinitesolar);
            GameRegistry.registerTileEntity(infinitesolar.getTileEntityClass(), infinitesolar.getRegistryName().toString());
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(worsesolar.createItemBlock());

        if (ModConfig.infiniteSolars.enabled) registry.register(infinitesolar.createItemBlock());
    }

    public static void registerModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(worsesolar), 0, new ModelResourceLocation("worsesolars:worse_solar_panel_normal"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(worsesolar), 2, new ModelResourceLocation("worsesolars:worse_solar_panel_decayed"));

        infinitesolar.registerItemModel(Item.getItemFromBlock(infinitesolar));
    }
}
