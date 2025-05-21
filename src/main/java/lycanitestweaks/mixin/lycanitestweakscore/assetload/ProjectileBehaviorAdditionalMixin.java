package lycanitestweaks.mixin.lycanitestweakscore.assetload;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.info.projectile.behaviours.ProjectileBehaviour;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.info.projectile.behaviours.ProjectileBehaviourAdvancedFireProjectiles;
import lycanitestweaks.info.projectile.behaviours.ProjectileBehaviourTargetedForce;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileBehaviour.class)
public abstract class ProjectileBehaviorAdditionalMixin {

    @Unique
    private final static String ADVANCED_FIRE_PROJECTILES = LycanitesTweaks.MODID + ":advancedfireProjectiles";
    @Unique
    private final static String TARGETED_FORCE = LycanitesTweaks.MODID + ":targetedForce";

    @ModifyReturnValue(
            method = "createFromJSON",
            at = @At(value = "RETURN", ordinal = 0),
            remap = false
    )
    private static ProjectileBehaviour lycanitesTweaks_lycanitesMobsProjectileBehaviour_createFromJSON(ProjectileBehaviour original, @Local(argsOnly = true) JsonObject json, @Local String type){
        if(ADVANCED_FIRE_PROJECTILES.equals(type)){
            ProjectileBehaviour projectileBehaviour = new ProjectileBehaviourAdvancedFireProjectiles();
            projectileBehaviour.type = type;
            projectileBehaviour.loadFromJSON(json);
            return projectileBehaviour;
        }
        if(TARGETED_FORCE.equals((type))){
            ProjectileBehaviour projectileBehaviour = new ProjectileBehaviourTargetedForce();
            projectileBehaviour.type = type;
            projectileBehaviour.loadFromJSON(json);
            return projectileBehaviour;
        }
        return original;
    }
}
