package prospectpyxis.worsesolars.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import prospectpyxis.worsesolars.WorseSolars;

public class BlockBase extends Block {

    protected String name;

    public BlockBase(Material mat, String name) {
        super(mat);

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public void registerItemModel(Item iB) {
        WorseSolars.proxy.registerItemRenderer(iB, 0, name);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
