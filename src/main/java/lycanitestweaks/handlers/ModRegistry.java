package lycanitestweaks.handlers;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.potion.PotionConsumed;
import lycanitestweaks.potion.PotionVoided;

@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
public class ModRegistry {

        public static void init() {

        }

        @SubscribeEvent
        public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
                event.getRegistry().register(PotionConsumed.INSTANCE);
                event.getRegistry().register(PotionVoided.INSTANCE);
        }
}