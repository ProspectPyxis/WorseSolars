package prospectpyxis.worsesolars.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import prospectpyxis.pyxislib.utils.info.BlockInformation;
import prospectpyxis.worsesolars.ModSettings;
import prospectpyxis.worsesolars.WorseSolars;
import prospectpyxis.worsesolars.block.BlockInfiniteSolar;
import prospectpyxis.worsesolars.block.BlockWorseSolar;
import prospectpyxis.worsesolars.item.ItemBlockInfiniteSolar;
import prospectpyxis.worsesolars.item.ItemBlockWorseSolar;

import java.util.ArrayList;

public class BlockRegisterer {

    public static ArrayList<BlockInformation> blocksList = new ArrayList<>();

    public static void preInit() {
        blocksList.add(new BlockInformation(new BlockWorseSolar())
                .setItemBlockByClass(ItemBlockWorseSolar.class)
                .addItemBlockModel("worse_solar_panel_normal", 0)
                .addItemBlockModel("worse_solar_panel_decayed", 2));
        blocksList.add(new BlockInformation(new BlockInfiniteSolar())
                .setEnabled(ModSettings.infiniteSolars.enabled)
                .setItemBlockByClass(ItemBlockInfiniteSolar.class));
    }

    @SuppressWarnings("deprecation")
    public static void register(IForgeRegistry<Block> registry) {
        for (BlockInformation b : blocksList) {
            b.registerBlock(registry);
            b.registerTileEntity();
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for (BlockInformation b : blocksList) {
            b.itemBlockInfo.registerItem(registry);
        }
    }

    public static void registerModels() {
        for (BlockInformation b : blocksList) {
            b.itemBlockInfo.registerItemModel(WorseSolars.proxy);
        }
    }
}
