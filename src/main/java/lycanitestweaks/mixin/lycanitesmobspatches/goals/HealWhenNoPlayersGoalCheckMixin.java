package lycanitestweaks.mixin.lycanitesmobspatches.goals;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.goals.actions.abilities.HealWhenNoPlayersGoal;
import net.minecraft.entity.ai.EntityAIBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HealWhenNoPlayersGoal.class)
public abstract class HealWhenNoPlayersGoalCheckMixin extends EntityAIBase {

    @Shadow(remap = false)
    public boolean firstPlayerTargetCheck;

    @Shadow(remap = false)
    BaseCreatureEntity host;

    @ModifyExpressionValue(
            method = "updateTask",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;updateTick:J", ordinal = 0, remap = false)
    )
    public long lycanitesTweaks_lycanitesMobsHealWhenNoPlayersGoal_updateTaskTickTrue(long original){
        return 200L;
    }

    @Inject(
            method = "updateTask",
            at = @At("HEAD")
    )
    public void lycanitesTweaks_lycanitesMobsHealWhenNoPlayersGoal_updateTaskVarDoesCheck(CallbackInfo ci){
        this.firstPlayerTargetCheck = this.firstPlayerTargetCheck || (this.host.updateTick % 200L == 0L);
    }
}
