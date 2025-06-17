package lycanitestweaks.handlers.features.effect;

import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.config.major.ItemTweaksConfig;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
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

        if(ForgeConfigHandler.mixinPatchesConfig.fixNVCuringBlindness){
            if(entity.isPotionActive(MobEffects.NIGHT_VISION)
                    && event.getPotionEffect().getPotion().equals(MobEffects.BLINDNESS))
                event.setResult(Event.Result.DENY);
        }

        if(ForgeConfigHandler.minorFeaturesConfig.repulsionWeight){
            if(event.getPotionEffect().getPotion().equals(ObjectManager.getEffect("repulsion"))){
                ExtendedEntity extendedEntity = ExtendedEntity.getForEntity(event.getEntityLiving());
                if(extendedEntity != null && extendedEntity.isPickedUp()){
                    if(extendedEntity.pickedUpByEntity instanceof BaseCreatureEntity) ((BaseCreatureEntity) extendedEntity.pickedUpByEntity).dropPickupEntity();
                    else extendedEntity.setPickedUpByEntity(null);
                }
            }
        }

        if(ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.customItemCureEffectList) {
            if (entity.isPotionActive(ObjectManager.getEffect("cleansed")) &&
                    ItemTweaksConfig.getCleansedCureEffects().contains(event.getPotionEffect().getPotion().getRegistryName())) {
                event.setResult(Event.Result.DENY);
            } else if (entity.isPotionActive(ObjectManager.getEffect("immunization")) &&
                    ItemTweaksConfig.getImmunizationCureEffects().contains(event.getPotionEffect().getPotion().getRegistryName())) {
                event.setResult(Event.Result.DENY);
            }


            if (event.getPotionEffect().getPotion() == ObjectManager.getEffect("cleansed"))
                Helpers.cureActiveEffectsFromResourceSet(entity, ItemTweaksConfig.getCleansedCureEffects());
            else if (event.getPotionEffect().getPotion() == ObjectManager.getEffect("immunization"))
                Helpers.cureActiveEffectsFromResourceSet(entity, ItemTweaksConfig.getImmunizationCureEffects());
        }
    }
}
