package lycanitestweaks.mixin.lycanitesmobsfeatures.configurablestats.otherscaledeffects;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityBarghest;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityBarghest.class)
public abstract class EntityBarghestEffectsCapMixin {

    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityBarghest;getEffectDuration(I)I", remap = false),
            remap = true
    )
    public int lycanitesTweaks_lycanitesMobsEntityBarghest_onLivingUpdateEffectLevelLimit(int original){
        if(ForgeConfigHandler.getLevelLimitedEffects().containsKey("barghest")){
            return Helpers.getEffectDurationLevelLimited((BaseCreatureEntity)(Object)this, 5, ForgeConfigHandler.getLevelLimitedEffects().get("barghest"));
        }
        return original;
    }
}
