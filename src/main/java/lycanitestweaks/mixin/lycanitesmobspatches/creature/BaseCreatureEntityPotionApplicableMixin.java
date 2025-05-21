package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityPotionApplicableMixin extends EntityLiving {

    public BaseCreatureEntityPotionApplicableMixin(World worldIn) {
        super(worldIn);
    }

    @ModifyReturnValue(
            method = "isPotionApplicable",
            at = @At(value = "RETURN", ordinal = 1)
    )
    public boolean lycanitesTweaks_lycanitesBaseCreatureEntity_isPotionApplicable(boolean original, @Local(argsOnly = true) PotionEffect potionEffect){
        return super.isPotionApplicable(potionEffect);
    }
}
