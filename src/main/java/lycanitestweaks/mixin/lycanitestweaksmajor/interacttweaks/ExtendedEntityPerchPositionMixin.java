package lycanitestweaks.mixin.lycanitestweaksmajor.interacttweaks;

import com.lycanitesmobs.core.entity.ExtendedEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ExtendedEntity.class)
public abstract class ExtendedEntityPerchPositionMixin {

    @ModifyConstant(
            method = "getPerchPosition",
            constant = @Constant(doubleValue = 90.0D),
            remap = false
    )
    public double lycanitesTweaks_lycanitesExtendedEntity_getPerchPositionAngle(double constant){
        return ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.perchAngle;
    }

    @ModifyConstant(
            method = "getPerchPosition",
            constant = @Constant(doubleValue = 0.7D),
            remap = false
    )
    public double lycanitesTweaks_lycanitesExtendedEntity_getPerchPositionDistance(double constant){
        return ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.perchDistance;
    }
}
