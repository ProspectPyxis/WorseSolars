package prospectpyxis.worsesolars.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import prospectpyxis.pyxislib.utils.ItemUtils;
import prospectpyxis.pyxislib.utils.info.ItemInformation;
import prospectpyxis.worsesolars.WorseSolars;

public class ItemRegisterer {

    public static ItemInformation solarcell = new ItemInformation(ItemUtils.SimpleItem.makeNew("solar_cell", WorseSolars.modid, CreativeTabs.MATERIALS));

    public static void register(IForgeRegistry<Item> registry) {
        solarcell.registerItem(registry);
    }

    public static void registerModels() {
        solarcell.registerItemModel(WorseSolars.proxy);
    }
}
