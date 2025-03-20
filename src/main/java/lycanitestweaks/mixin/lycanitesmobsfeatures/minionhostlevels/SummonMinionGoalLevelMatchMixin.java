package lycanitestweaks.mixin.lycanitesmobsfeatures.minionhostlevels;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.goals.BaseGoal;
import com.lycanitesmobs.core.entity.goals.actions.abilities.SummonMinionsGoal;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SummonMinionsGoal.class)
public abstract class SummonMinionGoalLevelMatchMixin extends BaseGoal {

    public SummonMinionGoalLevelMatchMixin(BaseCreatureEntity setHost) {
        super(setHost);
    }

    @Inject(
            method = "summonMinion",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;setSizeScale(D)V"),
            remap = false
    )
    protected void lycanitestweaks_lycanitesSummonMinionsGoal_summonMinion(EntityLivingBase target, CallbackInfo ci, @Local BaseCreatureEntity minionCreature){
        minionCreature.applyLevel(host.getLevel());
    }
}
