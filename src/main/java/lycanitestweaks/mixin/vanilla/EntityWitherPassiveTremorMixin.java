package lycanitestweaks.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import lycanitestweaks.util.LycanitesMobsWrapper;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.entity.boss.EntityWither$1")
public abstract class EntityWitherPassiveTremorMixin {

    @ModifyReturnValue(
            method = "apply*",
            at = @At("RETURN")
    )
    public boolean lycanitesTweaks_vanillaEntityWitherPredicate_apply(boolean original, @Local(argsOnly = true) Entity p_apply_1_){
        return original && !LycanitesMobsWrapper.isTremor(p_apply_1_);
    }
}
