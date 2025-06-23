package lycanitestweaks.mixin.lycanitestweaksminor.potiontweaks;

import com.lycanitesmobs.PotionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionEffects.class)
public abstract class PotionEffectsHealRoundingMixin {

    @Redirect(
            method = "onEntityHeal",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;ceil(D)D"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesMobsPotionEffects_onEntityHealRejuvRounding(double exactHealing){
        return exactHealing;
    }

    @Redirect(
            method = "onEntityHeal",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;floor(D)D"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesMobsPotionEffects_onEntityHealDecayRounding(double exactHealing){
        return exactHealing;
    }
}
