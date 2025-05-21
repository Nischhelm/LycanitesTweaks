package lycanitestweaks.handlers.features.effect;

import com.lycanitesmobs.ObjectManager;
import lycanitestweaks.handlers.config.ItemTweaksConfig;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemCuringEffectsHandler {

    @SubscribeEvent
    public static void itemCuringEffectApplicable(PotionEvent.PotionApplicableEvent event){
        if(event.isCanceled()) return;
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) return;
        if(entity.getEntityWorld().isRemote) return;

        if(entity.isPotionActive(ObjectManager.getEffect("cleansed")) &&
                ItemTweaksConfig.getCleansedCureEffects().contains(event.getPotionEffect().getPotion().getRegistryName())){
            event.setResult(Event.Result.DENY);
        }
        else if(entity.isPotionActive(ObjectManager.getEffect("immunization")) &&
                ItemTweaksConfig.getImmunizationCureEffects().contains(event.getPotionEffect().getPotion().getRegistryName())){
            event.setResult(Event.Result.DENY);
        }


        if(event.getPotionEffect().getPotion() == ObjectManager.getEffect("cleansed"))
            Helpers.cureActiveEffectsFromResourceSet(entity, ItemTweaksConfig.getCleansedCureEffects());
        else if(event.getPotionEffect().getPotion() == ObjectManager.getEffect("immunization"))
            Helpers.cureActiveEffectsFromResourceSet(entity, ItemTweaksConfig.getImmunizationCureEffects());
    }
}
