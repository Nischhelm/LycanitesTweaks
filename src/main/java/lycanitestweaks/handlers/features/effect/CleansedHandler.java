package lycanitestweaks.handlers.features.effect;

import com.lycanitesmobs.ObjectManager;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CleansedHandler {

    @SubscribeEvent
    public static void cleansedEffectApplicable(PotionEvent.PotionApplicableEvent event){
        if(event.isCanceled()) return;
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) return;
        if(entity.getEntityWorld().isRemote) return;

        if(entity.isPotionActive(ObjectManager.getEffect("cleansed")) &&
                ForgeConfigHandler.getCleansedCureEffects().contains(event.getPotionEffect().getPotion().getRegistryName())){
            event.setResult(Event.Result.DENY);
        }
    }
}
