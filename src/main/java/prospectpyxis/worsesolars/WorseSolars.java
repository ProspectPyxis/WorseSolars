package prospectpyxis.worsesolars;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import prospectpyxis.worsesolars.proxy.CommonProxy;
import prospectpyxis.worsesolars.registry.BlockRegisterer;
import prospectpyxis.worsesolars.registry.ItemRegisterer;
import prospectpyxis.worsesolars.registry.SoundRegisterer;

@Mod(modid = WorseSolars.modid, name = WorseSolars.name, version = WorseSolars.version, dependencies = WorseSolars.dependencies)
public class WorseSolars {

    public static final String modid = "worsesolars";
    public static final String name = "Worse Solars";
    public static final String version = "1.12.2-1.3";
    public static final String dependencies = "required-after:pyxislib@1.12.2-0.3";

    @Mod.Instance(modid)
    public static WorseSolars instance;

    public static Logger logger;

    @SidedProxy(serverSide = "prospectpyxis.worsesolars.proxy.ServerProxy", clientSide = "prospectpyxis.worsesolars.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("Now loading " + name);
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