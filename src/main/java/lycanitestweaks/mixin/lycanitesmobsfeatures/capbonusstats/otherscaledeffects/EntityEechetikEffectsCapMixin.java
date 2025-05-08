package lycanitestweaks.mixin.lycanitesmobsfeatures.capbonusstats.otherscaledeffects;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityEechetik;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityEechetik.class)
public abstract class EntityEechetikEffectsCapMixin {

    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityEechetik;getEffectDuration(I)I", remap = false),
            remap = true
    )
    public int lycanitesTweaks_lycanitesMobsEntityEechetik_onLivingUpdateEffectLevelLimit(int original){
        if(ForgeConfigHandler.getLevelLimitedEffects().containsKey("eechetik")){
            return Helpers.getEffectDurationLevelLimited((BaseCreatureEntity)(Object)this, 2, ForgeConfigHandler.getLevelLimitedEffects().get("eechetik"));
        }
        return original;
    }
}
