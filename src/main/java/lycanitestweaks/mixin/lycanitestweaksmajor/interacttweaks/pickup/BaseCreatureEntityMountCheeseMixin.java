package lycanitestweaks.mixin.lycanitestweaksmajor.interacttweaks.pickup;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import lycanitestweaks.util.IBaseCreatureEntityTransformIntoBossMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMountCheeseMixin extends EntityLiving {

    public BaseCreatureEntityMountCheeseMixin(World world) {
        super(world);
    }

    @Inject(
            method = "canBeRidden",
            at = @At("HEAD"),
            cancellable = true
    )
    public void lycanitesTweaks_lycanitesMobsBaseCreatureEntity_canBeRiddenAntiMountCheese(Entity entity, CallbackInfoReturnable<Boolean> cir){
        if(ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountCheeseFixTransform){
            if(this instanceof IBaseCreatureEntityTransformIntoBossMixin
                    && ((IBaseCreatureEntityTransformIntoBossMixin) this).lycanitesTweaks$canTransformIntoBoss())
                ((IBaseCreatureEntityTransformIntoBossMixin) this).lycanitesTweaks$transformIntoBoss();
        }

        if(ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountCheeseFixFlying
                && Helpers.isPracticallyFlying((BaseCreatureEntity)(Object)this)) cir.setReturnValue(false);
        if(ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountCheeseFixMounted
                && this.isBeingRidden()) cir.setReturnValue(false);
        if(ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountCheeseFixNoClip
                && this.noClip) cir.setReturnValue(false);
    }
}
