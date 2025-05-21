package lycanitestweaks.mixin.lycanitestweaksminor.repulsionweight;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.entity.creature.EntityRoa;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRoa.class)
public class EntityRoaRepulsionWeightMixin {

    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z")
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityRoa_onLivingUpdate(boolean original, @Local EntityLivingBase entityLivingBase){
        return original || entityLivingBase.isPotionActive(ObjectManager.getEffect("repulsion"));
    }
}
