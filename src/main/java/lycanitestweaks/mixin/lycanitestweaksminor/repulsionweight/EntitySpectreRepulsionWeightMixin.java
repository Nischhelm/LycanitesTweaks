package lycanitestweaks.mixin.lycanitestweaksminor.repulsionweight;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.entity.creature.EntitySpectre;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntitySpectre.class)
public class EntitySpectreRepulsionWeightMixin {

    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z")
    )
    public boolean lycanitesTweaks_lycanitesMobsEntitySpectre_onLivingUpdate(boolean original, @Local EntityLivingBase entity){
        return original || entity.isPotionActive(ObjectManager.getEffect("repulsion"));
    }
}
