package lycanitestweaks.mixin.lycanitesmobsfeatures.cleansedcustomlist;

import com.lycanitesmobs.PotionBase;
import com.lycanitesmobs.PotionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PotionEffects.class)
public abstract class PotionEffectsCleansedApplicableMixin {

    // Cancel original handling
    @Redirect(
            method = "onEntityUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/ObjectManager;getEffect(Ljava/lang/String;)Lcom/lycanitesmobs/PotionBase;", ordinal = 15),
            remap = false
    )
    public PotionBase lycanitesTweaks_lycanitesPotionEffects_onEntityUpdate(String name){
        return null;
    }
}
