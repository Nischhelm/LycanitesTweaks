package lycanitestweaks.mixin.lycanitestweaksmajor.interacttweaks;

import com.lycanitesmobs.core.entity.RideableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RideableCreatureEntity.class)
public abstract class RideableCreatureEntityScaledFlightSpeedMixin extends TameableCreatureEntity {

    public RideableCreatureEntityScaledFlightSpeedMixin(World world) {
        super(world);
    }

    @ModifyArgs(
            method = "moveMountedWithHeading",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/RideableCreatureEntity;move(Lnet/minecraft/entity/MoverType;DDD)V")
    )
    public void lycanitesTweaks_RideableCreatureEntity_moveMountedWithHeadingFlySpeedScaled(Args args){
        double modifiedMotionX = this.motionX * (1 + ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledModifier * this.creatureStats.getSpeed());
        double modifiedMotionZ = this.motionZ * (1 + ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledModifier * this.creatureStats.getSpeed());

        if(ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledMaxmimum < 10D) {
            modifiedMotionX = MathHelper.clamp(modifiedMotionX, -ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledMaxmimum, ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledMaxmimum);
            modifiedMotionZ = MathHelper.clamp(modifiedMotionZ, -ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledMaxmimum, ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledMaxmimum);
            args.set(2, MathHelper.clamp(args.get(2), -ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledMaxmimum, ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.mountFlySpeedScaledMaxmimum));
        }

        args.set(1, modifiedMotionX);
        args.set(3, modifiedMotionZ);
    }
}
