package lycanitestweaks.mixin.lycanitesmobsfeatures.repulsionweight;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.entity.creature.EntityThresher;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityThresher.class)
public class EntityThresherRepulsionWeightMixin {

    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityRoa_onLivingUpdate(boolean original, @Local EntityLivingBase entityLivingBase){
        return original || entityLivingBase.isPotionActive(ObjectManager.getEffect("repulsion"));
    }
}
