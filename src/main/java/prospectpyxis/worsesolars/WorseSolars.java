package prospectpyxis.worsesolars;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import prospectpyxis.worsesolars.proxy.CommonProxy;
import prospectpyxis.worsesolars.registry.BlockRegisterer;
import prospectpyxis.worsesolars.registry.ItemRegisterer;
import prospectpyxis.worsesolars.registry.SoundRegisterer;

@Mod(modid = WorseSolars.modid, name = WorseSolars.name, version = WorseSolars.version)
public class WorseSolars {

    public static final String modid = "worsesolars";
    public static final String name = "Worse Solars";
    public static final String version = "1.2.1";

    @Mod.Instance(modid)
    public static WorseSolars instance;

    @SidedProxy(serverSide = "prospectpyxis.worsesolars.proxy.CommonProxy", clientSide = "prospectpyxis.worsesolars.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Now loading " + name);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ItemRegisterer.register(event.getRegistry());
            BlockRegisterer.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BlockRegisterer.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            SoundRegisterer.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ItemRegisterer.registerModels();
            BlockRegisterer.registerModels();
        }
    }
}