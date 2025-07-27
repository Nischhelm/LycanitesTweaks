package lycanitestweaks.mixin.lycanitestweaksminor.bosstweaks;

import com.lycanitesmobs.core.info.projectile.ProjectileManager;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ProjectileManager.class)
public abstract class ProjectileManager_TrackerRangeMixin {

    @ModifyConstant(
            method = "registerEntities",
            constant = @Constant(intValue = 40, ordinal = 1),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsProjectileManager_registerEntitiesBossTracking(int constant){
        return ForgeConfigHandler.minorFeaturesConfig.bossProjectileTrackingRange;
    }
}
