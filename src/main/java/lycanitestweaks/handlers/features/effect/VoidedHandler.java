package lycanitestweaks.handlers.features.effect;

import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.LycanitesTweaksRegistry;
import lycanitestweaks.potion.PotionVoided;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VoidedHandler {

    private static boolean isHandlingDamage = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void voidedBuffPurge(PotionEvent.PotionApplicableEvent event){
        if(event.isCanceled()) return;
        EntityLivingBase entity = event.getEntityLiving();
        if(entity == null) return;
        if(entity.getEntityWorld().isRemote) return;

        if(ForgeConfigHandler.server.effectsConfig.voidedDeniesBuffs && entity.isPotionActive(PotionVoided.INSTANCE))
            if(event.getPotionEffect().getPotion().isBeneficial()) event.setResult(Event.Result.DENY);

        if(ForgeConfigHandler.server.effectsConfig.voidedRemovesBuffs && event.getPotionEffect().getPotion() == PotionVoided.INSTANCE)
            Helpers.removeAllPositiveEffects(entity);
    }

    @SubscribeEvent
    public static void voidedCooldown(TickEvent.PlayerTickEvent event){
        EntityPlayer player = event.player;
        if(player == null || player.world.isRemote) return;

        if(ForgeConfigHandler.server.effectsConfig.voidedItemCooldown > 0 && player.isPotionActive(PotionVoided.INSTANCE)) {
            player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), ForgeConfigHandler.server.effectsConfig.voidedItemCooldown);
            player.getCooldownTracker().setCooldown(player.getHeldItemOffhand().getItem(), ForgeConfigHandler.server.effectsConfig.voidedItemCooldown);
        }
    }

    @SubscribeEvent
    public static void voidedCooldownDeny(PlayerInteractEvent.RightClickItem event){
        if(event.getEntityPlayer() == null) return;
        if(ForgeConfigHandler.server.effectsConfig.voidedItemCooldown > 0 && event.getEntityPlayer().isPotionActive(PotionVoided.INSTANCE))
            event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void voidedPiercing(LivingHurtEvent event){
        if(event.getEntityLiving() == null || event.getEntityLiving().getEntityWorld().isRemote) return;
        if(!event.getEntityLiving().isPotionActive(PotionVoided.INSTANCE)) return;

        if(((ForgeConfigHandler.server.effectsConfig.voidedPiecingEnvironment && event.getSource().getTrueSource() == null) ||
            ForgeConfigHandler.server.effectsConfig.voidedPiecingAll) &&
            !isHandlingDamage) {
            isHandlingDamage = true;

            // wtf are iframes and forge doing, this is to deal 1x piercing damage
            event.getEntityLiving().attackEntityFrom(LycanitesTweaksRegistry.VOIDED, event.getAmount() * 2.0F);
            event.setCanceled(true);
            isHandlingDamage = false;
        }
    }
}
