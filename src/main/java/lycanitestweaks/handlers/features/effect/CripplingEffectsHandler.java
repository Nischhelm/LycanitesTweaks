package lycanitestweaks.handlers.features.effect;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.potion.PotionCripplingBase;
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

public class CripplingEffectsHandler {

    private static DamageSource source;
    private static float damage = 0;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void cripplingBuffPurge(PotionEvent.PotionApplicableEvent event) {
        if (event.isCanceled()) return;
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null || entity.getEntityWorld().isRemote) return;

        // bruh why does Rahovart alone keep failing these checks
        // it is denied here but he still has it
        if(event.getPotionEffect().getPotion() instanceof PotionCripplingBase){
            if(entity instanceof EntityPlayer
                && (((EntityPlayer) entity).isCreative() || ((EntityPlayer) entity).isSpectator()))
                event.setResult(Event.Result.DENY);
            else if((entity instanceof BaseCreatureEntity && ((BaseCreatureEntity) entity).isBoss()))
                event.setResult(Event.Result.DENY);
//            else if(ForgeConfigHandler.client.debugLoggerTrigger)
//                if(entity instanceof BaseCreatureEntity) LycanitesTweaks.LOGGER.log(Level.INFO, "Applying cripple effect to Creature {}", ((BaseCreatureEntity)entity).creatureInfo.getEntityClass());
//                else LycanitesTweaks.LOGGER.log(Level.INFO, "Applying cripple effect to Entity {}", entity.getClass());
        }
        for(PotionCripplingBase potion : PotionCripplingBase.instanceSet){
            if(entity.isPotionActive(potion) && potion.shouldDenyBuffs())
                if(!event.getPotionEffect().getPotion().isBadEffect()) event.setResult(Event.Result.DENY);
            if(event.getPotionEffect().getPotion() == potion && potion.shouldRemoveBuffs())
                Helpers.removeAllPositiveEffects(entity);
        }
    }

    @SubscribeEvent
    public static void cripplingCooldownDeny(PlayerInteractEvent.RightClickItem event) {
        if(event.getEntityPlayer() == null) return;
        for(PotionCripplingBase potion : PotionCripplingBase.instanceSet){
            if(event.getEntityPlayer().isPotionActive(potion) && potion.getItemCooldown() > 0)
                event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void cripplingPiercingHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() == null || event.getEntityLiving().getEntityWorld().isRemote) return;

        for(PotionCripplingBase potion : PotionCripplingBase.instanceSet) {
            if(event.getEntityLiving().isPotionActive(potion)){
                if(potion.shouldPierceAll() || potion.shouldPierceEnvironment() && event.getSource().getTrueSource() == null){
                    source = event.getSource();
                    damage = event.getAmount();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void cripplingPiercingDamage(LivingDamageEvent event) {
        if (event.getEntityLiving() == null || event.getEntityLiving().getEntityWorld().isRemote) return;

        for(PotionCripplingBase potion : PotionCripplingBase.instanceSet) {
            if(event.getEntityLiving().isPotionActive(potion)){
                if(source == event.getSource()) {
                    event.setAmount(Math.max(damage, event.getAmount()));
                    damage = 0;
                }
            }
        }
    }
}
