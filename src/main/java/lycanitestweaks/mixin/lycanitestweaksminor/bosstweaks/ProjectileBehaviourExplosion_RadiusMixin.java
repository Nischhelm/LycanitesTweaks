package lycanitestweaks.mixin.lycanitestweaksminor.bosstweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.info.projectile.behaviours.ProjectileBehaviourExplosion;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileBehaviourExplosion.class)
public abstract class ProjectileBehaviourExplosion_RadiusMixin {

    @ModifyExpressionValue(
            method = "onProjectileImpact",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseProjectileEntity;getThrower()Lnet/minecraft/entity/EntityLivingBase;", ordinal = 0)
    )
    public EntityLivingBase lycanitesTweaks_lycanitesMobsProjectileBehaviourExplosion_onProjectileImpact(EntityLivingBase original){
        return null;
    }
}
