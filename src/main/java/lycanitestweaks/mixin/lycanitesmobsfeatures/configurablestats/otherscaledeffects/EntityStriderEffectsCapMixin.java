package lycanitestweaks.mixin.lycanitesmobsfeatures.configurablestats.otherscaledeffects;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityStrider;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityStrider.class)
public abstract class EntityStriderEffectsCapMixin {

    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityStrider;getEffectDuration(I)I", remap = false),
            remap = true
    )
    public int lycanitesTweaks_lycanitesMobsEntityStrider_onLivingUpdateEffectLevelLimit(int original){
        if(ForgeConfigHandler.getLevelLimitedEffects().containsKey("strider")){
            return Helpers.getEffectDurationLevelLimited((BaseCreatureEntity)(Object)this, 5, ForgeConfigHandler.getLevelLimitedEffects().get("strider"));
        }
        return original;
    }
}
