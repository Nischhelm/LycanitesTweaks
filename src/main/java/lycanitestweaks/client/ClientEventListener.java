package lycanitestweaks.client;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.item.special.ItemSoulgazer;
import com.lycanitesmobs.core.item.temp.ItemStaffSummoning;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventListener {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if(!ForgeConfigHandler.server.pmlConfig.playerMobLevelCapability) return;
        if (event.getItemStack().getItem() instanceof ItemSoulgazer) {
            if(ForgeConfigHandler.featuresMixinConfig.playerMobLevelJSONSpawner)
                event.getToolTip().add(LanguageManager.translate("soulgazer.description.pmlspawner"));
            if(ForgeConfigHandler.featuresMixinConfig.playerMobLevelMainBosses)
                event.getToolTip().add(LanguageManager.translate("soulgazer.description.pmlboss"));
        }
        else if(event.getItemStack().getItem() instanceof ItemStaffSummoning) {
            if(ForgeConfigHandler.featuresMixinConfig.playerMobLevelSummonStaff)
                event.getToolTip().add(LanguageManager.translate("summonstaff.description.pmlsummon"));
        }
    }
}
