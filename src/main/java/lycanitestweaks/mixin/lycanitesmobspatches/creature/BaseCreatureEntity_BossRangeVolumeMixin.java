package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntity_BossRangeVolumeMixin {

    @Shadow
    protected abstract float getSoundVolume();

    @ModifyArg(
            method = "fireProjectile(Lcom/lycanitesmobs/core/entity/BaseProjectileEntity;Lnet/minecraft/entity/Entity;FFLnet/minecraft/util/math/Vec3d;FFF)Lcom/lycanitesmobs/core/entity/BaseProjectileEntity;",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;playSound(Lnet/minecraft/util/SoundEvent;FF)V"),
            index = 1
    )
    public float lycanitesTweaks_lycanitesMobsBaseCreatureEntity_fireProjectileSoundVolume(float volume){
        return this.getSoundVolume();
    }
}
