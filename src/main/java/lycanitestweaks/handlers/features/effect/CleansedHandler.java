package lycanitestweaks.handlers.features.effect;

import com.lycanitesmobs.ObjectManager;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

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
        if(event.getPotionEffect().getPotion() == ObjectManager.getEffect("cleansed")) cleansedActiveEffects(entity);
    }

    public static void cleansedActiveEffects(EntityLivingBase entity){
        List<Potion> potionsToRemove = new ArrayList<>();
        for(PotionEffect effect : entity.getActivePotionEffects()){
            if(ForgeConfigHandler.getCleansedCureEffects().contains(effect.getPotion().getRegistryName()))
                potionsToRemove.add(effect.getPotion());
        }
        for(Potion potion : potionsToRemove){
            entity.removePotionEffect(potion);
        }
    }
}
