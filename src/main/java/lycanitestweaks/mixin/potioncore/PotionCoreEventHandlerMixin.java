package lycanitestweaks.mixin.potioncore;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.tmtravlr.potioncore.PotionCoreEventHandler;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionCoreEventHandler.class)
public abstract class PotionCoreEventHandlerMixin {

    /**
     * Fix PotionCore forcibly overwriting player motionY breaking motion increases set by other mods
     * Based on fix in Bounceable by fonnymunkey
     *
     * 1. Maintain "bouncing" behavior of flying mobs
     * 2. Restore motionY jumping abilities of mobs
     */
    @Redirect(
            method = "onLivingJump",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;motionY:D", ordinal = 2)
    )
    private static void lycanitesTweaks_potionCoreEventHandler_onLivingJump(EntityLivingBase instance, double value) {
        if(instance instanceof BaseCreatureEntity ||
                (ForgeConfigHandler.server.fixAllMobsPotionCoreJump && instance instanceof EntityLiving)) {
            instance.motionY = Math.max(value, instance.motionY);
        }
        else instance.motionY = value;
    }
}
