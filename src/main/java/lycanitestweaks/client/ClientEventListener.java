package lycanitestweaks.client;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.special.ItemSoulgazer;
import com.lycanitesmobs.core.item.temp.ItemStaffSummoning;
import lycanitestweaks.capability.ILycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.LycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.config.PlayerMobLevelsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventListener {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(event.getEntityPlayer());
        if(pml != null) {
            if (event.getItemStack().getItem() instanceof ItemSoulgazer) {
                if(!PlayerMobLevelsConfig.getPmlBonusCategorySoulgazer().isEmpty()) {
                    event.getToolTip().addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(
                            I18n.format("item.soulgazer.description.pmlsoulgazer"), ItemBase.DESCRIPTION_WIDTH));
                }
                if(event.getEntityPlayer().isCreative()){
                    event.getToolTip().add(I18n.format("item.soulgazer.description.pmlcreative",
                            pml.getTotalEnchantmentLevels(),
                            pml.getHighestLevelPetSoulbound()
                    ));
                }

            }
            else if (event.getItemStack().getItem() instanceof ItemStaffSummoning) {
                if(PlayerMobLevelsConfig.getPmlBonusCategories().containsKey(PlayerMobLevelsConfig.BonusCategory.SummonMinion)) {
                    event.getToolTip().addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(
                            I18n.format("item.summonstaff.description.pmlsummon"), ItemBase.DESCRIPTION_WIDTH));
                }
            }
        }

        if(ForgeConfigHandler.integrationConfig.soulgazerBauble){
            if (event.getItemStack().getItem() instanceof ItemSoulgazer) {
                ILycanitesTweaksPlayerCapability playerKeybinds = LycanitesTweaksPlayerCapability.getForPlayer(event.getEntityPlayer());
                if (playerKeybinds != null) {
                    int autoID = playerKeybinds.getSoulgazerAutoToggle();
                    int manualID = playerKeybinds.getSoulgazerManualToggle() ? 1 : 2;
                    event.getToolTip().add(I18n.format("item.soulgazer.description.keybind.auto." + autoID));
                    event.getToolTip().add(I18n.format("item.soulgazer.description.keybind.manual." + manualID));
                }
            }
        }
    }
}
