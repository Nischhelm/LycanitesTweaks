package lycanitestweaks.mixin.lycanitesmobsfeatures.creature;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.FearEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FearEntity.class)
public abstract class FearEntityMixin extends BaseCreatureEntity {

    public FearEntityMixin(World world) {
        super(world);
    }

    // Add constant drop if outside range, teleports fear to entity before teleporting victim to fear
    @WrapWithCondition(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/FearEntity;pickupEntity(Lnet/minecraft/entity/EntityLivingBase;)V", remap = false),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesFearEntity_onLivingUpdate(FearEntity instance, EntityLivingBase entityLivingBase){
        if(instance.getDistance(entityLivingBase) > ForgeConfigHandler.server.pickUpDistance){
            this.dropPickupEntity();
            return false;
        }
        return true;
    }
}
