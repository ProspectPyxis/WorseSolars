package prospectpyxis.worsesolars.registry;

import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class SoundRegisterer {

    public static ResourceLocation shortoutRL = new ResourceLocation("worsesolars", "solar.shortout");
    public static SoundEvent shortout = new SoundEvent(shortoutRL).setRegistryName("solar.shortout");

    public static void register(IForgeRegistry<SoundEvent> registry) {
        registry.registerAll(
                shortout
        );
    }
}
