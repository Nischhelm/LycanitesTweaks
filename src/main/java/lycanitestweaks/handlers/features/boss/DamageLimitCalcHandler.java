package lycanitestweaks.handlers.features.boss;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DamageLimitCalcHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateDPSCalc(LivingDamageEvent event){
        if(event.getEntityLiving() == null) return;
        if(!(event.getEntityLiving() instanceof BaseCreatureEntity)) return;
        BaseCreatureEntity boss = (BaseCreatureEntity)event.getEntityLiving();
        boss.onDamage(event.getSource(), event.getAmount());
    }
}
