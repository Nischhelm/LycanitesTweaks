package lycanitestweaks.mixin.lycanitesmobsfeatures.creature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityPickupMixin extends EntityLiving {

    @Shadow(remap = false)
    public EntityLivingBase pickupEntity;

    public BaseCreatureEntityPickupMixin(World worldIn) {
        super(worldIn);
    }

    // Use config value
    @ModifyConstant(
            method = "onLivingUpdate",
            constant = @Constant(doubleValue = 32.0D)
    )
    public double lycanitesTweaks_lycanitesBaseCreatureEntity_configPickUpDistance(double constant){
        return ForgeConfigHandler.server.pickUpDistance;
    }

    // Fix calculation (was sqrt sqrt)
    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;sqrt(D)D"),
            remap = true
    )
    public double lycanitesTweaks_lycanitesBaseCreatureEntity_onLivingUpdate(double original){
        return this.getDistance(this.pickupEntity);
    }
}
