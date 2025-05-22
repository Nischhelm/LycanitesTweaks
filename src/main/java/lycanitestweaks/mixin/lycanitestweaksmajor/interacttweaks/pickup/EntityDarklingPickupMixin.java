package lycanitestweaks.mixin.lycanitestweaksmajor.interacttweaks.pickup;

import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityDarkling;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityDarkling.class)
public abstract class EntityDarklingPickupMixin extends TameableCreatureEntity {

    @Shadow(remap = false)
    public abstract boolean hasLatchTarget();
    @Shadow(remap = false)
    public abstract EntityLivingBase getLatchTarget();
    @Shadow(remap = false)
    public abstract void setLatchTarget(EntityLivingBase entity);

    public EntityDarklingPickupMixin(World world) {
        super(world);
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityDarkling;hasLatchTarget()Z", ordinal = 1, remap = false)
    )
    public void lycanitesTweaks_lycanitesMobsEntityDarkling_onLivingUpdatePickupRange(CallbackInfo ci){
        if(this.hasLatchTarget() && ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.pickupChecksDarkling) {
            EntityLivingBase latchTarget = this.getLatchTarget();
            if (this.getDistance(latchTarget) > ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.pickUpDistance) {
                this.setPosition(this.posX, this.posY + 3, this.posZ); // Required to not send darkling into the ground
                this.setLatchTarget(null);
            }
        }
    }
}
