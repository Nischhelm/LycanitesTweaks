package lycanitestweaks.handlers.features.effect;

import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.potion.PotionVoided;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VoidedHandler {

    private static DamageSource source;
    private static float damage = 0;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void voidedBuffPurge(PotionEvent.PotionApplicableEvent event){
        if(event.isCanceled()) return;
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) return;
        if(entity.getEntityWorld().isRemote) return;

        if(event.getPotionEffect().getPotion() == PotionVoided.INSTANCE &&
                entity instanceof EntityPlayer &&
                (((EntityPlayer) entity).isCreative() || ((EntityPlayer) entity).isSpectator()))
            event.setResult(Event.Result.DENY);

        if(ForgeConfigHandler.server.effectsConfig.voidedDeniesBuffs && entity.isPotionActive(PotionVoided.INSTANCE))
            if(!event.getPotionEffect().getPotion().isBadEffect()) event.setResult(Event.Result.DENY);

        if(ForgeConfigHandler.server.effectsConfig.voidedRemovesBuffs && event.getPotionEffect().getPotion() == PotionVoided.INSTANCE)
            Helpers.removeAllPositiveEffects(entity);
    }

    @SubscribeEvent
    public static void voidedCooldownDeny(PlayerInteractEvent.RightClickItem event){
        if(event.getEntityPlayer() == null) return;
        if(ForgeConfigHandler.server.effectsConfig.voidedItemCooldown > 0 && event.getEntityPlayer().isPotionActive(PotionVoided.INSTANCE))
            event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void voidedPiercingHurt(LivingHurtEvent event){
        if(event.getEntityLiving() == null || event.getEntityLiving().getEntityWorld().isRemote) return;
        if(!event.getEntityLiving().isPotionActive(PotionVoided.INSTANCE)) return;

        if((ForgeConfigHandler.server.effectsConfig.voidedPiecingEnvironment && event.getSource().getTrueSource() == null) ||
            ForgeConfigHandler.server.effectsConfig.voidedPiecingAll) {
            source = event.getSource();
            damage = event.getAmount();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void voidedPiercingDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() == null || event.getEntityLiving().getEntityWorld().isRemote) return;
        if (!event.getEntityLiving().isPotionActive(PotionVoided.INSTANCE)) return;

        if(source == event.getSource()) {
            event.setAmount(Math.max(damage, event.getAmount()));
            damage = 0;
        }
    }
}
