package lycanitestweaks.mixin.lycanitestweaksminor.bosstweaks;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityBossDeathMinionsMixin {

    @Shadow(remap = false)
    public List<EntityLivingBase> minions;
    @Shadow(remap = false)
    public abstract boolean isBoss();

    @Inject(
            method = "onDeath",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;getMasterTarget()Lnet/minecraft/entity/EntityLivingBase;", remap = false)
    )
    public void lycanitesTweaks_lycanitesMobsBaseCreatureEntity_onDeathBossDeadMinion(DamageSource damageSource, CallbackInfo ci){
        if(this.isBoss())
            for(EntityLivingBase minion : this.minions) minion.setDead();
    }
}
