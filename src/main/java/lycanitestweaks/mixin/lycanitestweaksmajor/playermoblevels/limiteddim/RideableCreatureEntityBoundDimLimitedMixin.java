package lycanitestweaks.mixin.lycanitestweaksmajor.playermoblevels.limiteddim;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.core.entity.RideableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.config.major.PlayerMobLevelsConfig;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RideableCreatureEntity.class)
public abstract class RideableCreatureEntityBoundDimLimitedMixin extends TameableCreatureEntity {

    public RideableCreatureEntityBoundDimLimitedMixin(World world) {
        super(world);
    }

    @ModifyReturnValue(
            method = "hasSaddle",
            at = @At("RETURN"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsRideableCreatureEntity_hasSaddle(boolean original){
        if(this.isBoundPet()
                && ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlMinionLimitDimNoMount
                && PlayerMobLevelsConfig.isDimensionLimitedMinion(this.dimension))
            return false;
        return original;
    }
}
