package lycanitestweaks.handlers;

import lycanitestweaks.loot.EnchantWithMobLevels;
import lycanitestweaks.loot.HasMobLevels;
import lycanitestweaks.loot.ScaleWithMobLevels;
import net.minecraft.potion.Potion;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.potion.PotionConsumed;
import lycanitestweaks.potion.PotionVoided;

@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
public class ModRegistry {

        // wasted an hour wondering why it couldn't be like RLMixins
        public static void init() {
                if(ForgeConfigHandler.server.registerPMLLootCondition) LootConditionManager.registerCondition(new HasMobLevels.Serializer());
                if(ForgeConfigHandler.server.registerPMLLootFunction) {
                        LootFunctionManager.registerFunction(new EnchantWithMobLevels.Serializer());
                        LootFunctionManager.registerFunction(new ScaleWithMobLevels.Serializer());
                }
        }

        @SubscribeEvent
        public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
                event.getRegistry().register(PotionConsumed.INSTANCE);
                event.getRegistry().register(PotionVoided.INSTANCE);
        }
}