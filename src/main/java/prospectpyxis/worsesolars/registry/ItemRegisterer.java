package prospectpyxis.worsesolars.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import prospectpyxis.worsesolars.core.ItemBase;

public class ItemRegisterer {

    public static ItemBase solarcell = new ItemBase("solar_cell").setCreativeTab(CreativeTabs.MATERIALS);

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                solarcell
        );
    }

    public static void registerModels() {
        solarcell.registerItemModel();
    }
}
