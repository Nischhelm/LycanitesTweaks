package lycanitestweaks.mixin.lycanitesmobsfeatures.capbonusstats.otherscaledeffects;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityWarg;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EntityWarg.class)
public abstract class EntityWargEffectsCapMixin {

    @ModifyArgs(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;<init>(Lnet/minecraft/potion/Potion;II)V"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsEntityWarg_onLivingUpdateEffectLevelLimit(Args args){
        if(ForgeConfigHandler.getLevelLimitedEffects().containsKey("warg")){
            args.set(1, Helpers.getEffectDurationLevelLimited((BaseCreatureEntity)(Object)this, 1, ForgeConfigHandler.getLevelLimitedEffects().get("warg")));
            args.set(2, Helpers.getEffectAmplifierLevelLimited((BaseCreatureEntity)(Object)this, 1.0F, ForgeConfigHandler.getLevelLimitedEffects().get("warg")));
        }
    }
}
