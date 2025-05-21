package lycanitestweaks.mixin.lycanitestweaksminor.smitedundead;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseCreatureEntity.class)
public class BaseCreatureEntitySmitedUndeadMixin extends EntityLiving {

    public BaseCreatureEntitySmitedUndeadMixin(World worldIn) {
        super(worldIn);
    }

    @ModifyReturnValue(
            method = "getCreatureAttribute",
            at = @At("RETURN")
    )
    public EnumCreatureAttribute lycanitesTweaks_lycanitesBaseCreatureEntity_getCreatureAttribute(EnumCreatureAttribute original){
        if(this.isPotionActive(ObjectManager.getEffect("smited"))) return EnumCreatureAttribute.UNDEAD;
        return original;
    }
}
