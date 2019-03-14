package prospectpyxis.worsesolars.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import prospectpyxis.pyxislib.utils.ItemUtils;
import prospectpyxis.worsesolars.WorseSolars;

public class ItemRegisterer {

    public static Item solarcell = ItemUtils.newSimpleItem("solar_cell", CreativeTabs.MATERIALS);

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                solarcell
        );
    }

    public static void registerModels() {
        WorseSolars.proxy.registerItemRenderer(solarcell, 0, solarcell.getUnlocalizedName());
    }
}
