package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityPickupTargetMixin {

    @Shadow(remap = false)
    public abstract boolean hasPickupEntity();
    @Shadow(remap = false)
    public abstract EntityLivingBase getPickupEntity();

    @Inject(
            method = "canEntityBeSeen",
            at = @At("RETURN"),
            cancellable = true
    )
    public void lycanitesTweaks_lycanitesMobsBaseCreatureEntity_canEntityBeSeenPickupTarget(Entity target, CallbackInfoReturnable<Boolean> cir){
        if(this.hasPickupEntity() && this.getPickupEntity() == target) cir.setReturnValue(true);
    }
}
