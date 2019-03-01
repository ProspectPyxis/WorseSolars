package prospectpyxis.worsesolars.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import prospectpyxis.worsesolars.block.BlockWorseSolar;

public class BlockRegisterer {

    public static BlockWorseSolar worsesolar = new BlockWorseSolar();

    @SuppressWarnings("deprecation")
    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                worsesolar
        );

        GameRegistry.registerTileEntity(worsesolar.getTileEntityClass(), worsesolar.getRegistryName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(worsesolar.createItemBlock());
    }

    public static void registerModels() {
        worsesolar.registerItemModel(Item.getItemFromBlock(worsesolar));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(worsesolar), 0, new ModelResourceLocation("worsesolars:worse_solar_panel_normal"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(worsesolar), 2, new ModelResourceLocation("worsesolars:worse_solar_panel_decayed"));
    }
}
