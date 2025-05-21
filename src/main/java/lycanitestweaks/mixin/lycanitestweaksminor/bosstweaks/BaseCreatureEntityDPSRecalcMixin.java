package lycanitestweaks.mixin.lycanitestweaksminor.bosstweaks;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityDPSRecalcMixin {

    // Calc at Living Damage Lowest
    @Redirect(
            method = "attackEntityFrom",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;onDamage(Lnet/minecraft/util/DamageSource;F)V", remap = false)
    )
    private void lycanitesTweaks_lycanitesBaseCreatureEntity_attackEntityFrom(BaseCreatureEntity instance, DamageSource damageSrc, float damage){
        // no op
    }
}
