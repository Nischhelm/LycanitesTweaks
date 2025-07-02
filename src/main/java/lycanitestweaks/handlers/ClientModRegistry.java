package lycanitestweaks.handlers;

import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.client.keybinds.KeyHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID, value = Side.CLIENT)
public class ClientModRegistry {

    public static void init() {
        KeyHandler.init();
        MinecraftForge.EVENT_BUS.register(KeyHandler.class);
    }

    @SubscribeEvent
    public static void registerModelEvent(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(LycanitesTweaksRegistry.enchantedSoulkey, 0, new ModelResourceLocation(LycanitesTweaksRegistry.enchantedSoulkey.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(LycanitesTweaksRegistry.enchantedSoulkeyDiamond, 0, new ModelResourceLocation(LycanitesTweaksRegistry.enchantedSoulkeyDiamond.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(LycanitesTweaksRegistry.enchantedSoulkeyEmerald, 0, new ModelResourceLocation(LycanitesTweaksRegistry.enchantedSoulkeyEmerald.getRegistryName(), "inventory"));
    }
}