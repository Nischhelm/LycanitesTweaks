package lycanitestweaks.mixin.lycanitesmobsfeatures.smitedundead;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lycanitestweaks.util.LycanitesMobsWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseSmitedUndeadMixin extends Entity {

    public EntityLivingBaseSmitedUndeadMixin(World worldIn) {
        super(worldIn);
    }

    @ModifyReturnValue(
            method = "getCreatureAttribute",
            at = @At("RETURN"),
            remap = true
    )
    public EnumCreatureAttribute lycanitesTweaks_vanillaEntityLivingBase_getCreatureAttribute(EnumCreatureAttribute original){
        if(LycanitesMobsWrapper.hasSmitedEffect(this)) return EnumCreatureAttribute.UNDEAD;
        return original;
    }
}
