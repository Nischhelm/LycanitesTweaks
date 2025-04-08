package lycanitestweaks.handlers;

import lycanitestweaks.loot.EnchantWithMobLevels;
import lycanitestweaks.loot.HasMobLevels;
import lycanitestweaks.loot.IsVariant;
import lycanitestweaks.loot.ScaleWithMobLevels;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.potion.PotionConsumed;
import lycanitestweaks.potion.PotionVoided;

@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
public class LycanitesTweaksRegistry {

        public static SoundEvent SOULGAZER_CRAFTINGTABLE;
        public static SoundEvent SOULGAZER_PLAYER;

        // wasted an hour wondering why it couldn't be like RLMixins
        public static void init() {
                if(ForgeConfigHandler.server.pmlConfig.registerPMLLootCondition) {
                        LootConditionManager.registerCondition(new HasMobLevels.Serializer());
                        LootConditionManager.registerCondition(new IsVariant.Serializer());
                }
                if(ForgeConfigHandler.server.pmlConfig.registerPMLLootFunction) {
                        LootFunctionManager.registerFunction(new EnchantWithMobLevels.Serializer());
                        LootFunctionManager.registerFunction(new ScaleWithMobLevels.Serializer());
                }

                SOULGAZER_CRAFTINGTABLE = new SoundEvent(new ResourceLocation(LycanitesTweaks.MODID, "soulgazer_craftingtable")).setRegistryName("soulgazer_craftingtable");
                SOULGAZER_PLAYER = new SoundEvent(new ResourceLocation(LycanitesTweaks.MODID, "soulgazer_player")).setRegistryName("soulgazer_player");
        }

        @SubscribeEvent
        public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                event.getRegistry().register(SOULGAZER_CRAFTINGTABLE);
                event.getRegistry().register(SOULGAZER_PLAYER);
        }

        @SubscribeEvent
        public static void registerPotionEvent(RegistryEvent.Register<Potion> event) {
                if(ForgeConfigHandler.server.effectsConfig.registerConsumed) event.getRegistry().register(PotionConsumed.INSTANCE);
                if(ForgeConfigHandler.server.effectsConfig.registerVoided) event.getRegistry().register(PotionVoided.INSTANCE);
        }
}