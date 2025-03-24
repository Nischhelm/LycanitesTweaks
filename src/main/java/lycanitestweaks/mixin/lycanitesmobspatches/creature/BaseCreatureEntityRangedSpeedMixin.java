package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityRangedSpeedMixin {

    // NOT CORRECT SOLUTION
    // ADDRESS (--this.attackTime == 0) in AttackRangedGoal
    @ModifyReturnValue(
            method = "getRangedCooldown",
            at = @At("RETURN"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesBaseCreatureEntity_getRangedCooldown(int original){
        return Math.max(original, 1);
    }
}
