package lycanitestweaks.handlers.features.boss;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DamageLimitCalcHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateDPSCalc(LivingDamageEvent event){
        if(event.getEntityLiving() instanceof BaseCreatureEntity) {
            BaseCreatureEntity boss = (BaseCreatureEntity) event.getEntityLiving();
            boss.onDamage(event.getSource(), event.getAmount());
            if(ForgeConfigHandler.server.bossDamageLimitReducesAmount && boss.damageLimit > 0.0F)
                event.setAmount(Math.min(event.getAmount(), boss.damageLimit));
        }
    }
}
