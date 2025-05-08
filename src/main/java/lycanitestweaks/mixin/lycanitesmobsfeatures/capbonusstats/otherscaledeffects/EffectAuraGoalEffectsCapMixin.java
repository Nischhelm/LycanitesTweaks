package lycanitestweaks.mixin.lycanitesmobsfeatures.capbonusstats.otherscaledeffects;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.goals.BaseGoal;
import com.lycanitesmobs.core.entity.goals.actions.abilities.EffectAuraGoal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EffectAuraGoal.class)
public abstract class EffectAuraGoalEffectsCapMixin extends BaseGoal {

    @Shadow(remap = false)
    protected int effectSeconds;

    public EffectAuraGoalEffectsCapMixin(BaseCreatureEntity setHost) {
        super(setHost);
    }

    @ModifyExpressionValue(
            method = "updateTask",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;getEffectDuration(I)I", remap = false),
            remap = true
    )
    public int lycanitesTweaks_lycanitesMobsEffectAuraGoal_updateTaskEffectLevelLimit(int original){
        if(ForgeConfigHandler.getLevelLimitedEffects().containsKey("EffectAuraGoal")){
            return Helpers.getEffectDurationLevelLimited(this.host, this.effectSeconds, ForgeConfigHandler.getLevelLimitedEffects().get("EffectAuraGoal"));
        }
        return original;
    }
}
