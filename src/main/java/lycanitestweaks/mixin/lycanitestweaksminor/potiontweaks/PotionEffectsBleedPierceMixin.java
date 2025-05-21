package lycanitestweaks.mixin.lycanitestweaksminor.potiontweaks;

import com.lycanitesmobs.PotionEffects;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PotionEffects.class)
public abstract class PotionEffectsBleedPierceMixin {

    @ModifyArg(
            method = "onEntityUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"),
            index = 0
    )
    public DamageSource lycanitesTweaks_lycanitesPotionEffects_onEntityUpdateBleedPierce(DamageSource source){
        return source.setDamageIsAbsolute();
    }
}
