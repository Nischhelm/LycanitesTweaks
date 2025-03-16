package lycanitestweaks.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityOwnable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(targets = "net.minecraft.entity.monster.EntityIronGolem$1")
public abstract class EntityIronGolemNeutralTameableMixin {

    @ModifyReturnValue(
            method = "apply*",
            at = @At("RETURN"),
            remap = true
    )
    public boolean lycanitesTweaks_vanillaEntityIronGolemEntityAINearestAttackableTarget_apply(boolean original, @Local(argsOnly = true) EntityLiving p_apply_1_){
        return original && (!(p_apply_1_ instanceof IEntityOwnable) || ((IEntityOwnable)p_apply_1_).getOwner() == null);
    }
}
