package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.projectile.EntityHellfireBarrier;
import com.lycanitesmobs.core.entity.projectile.EntityHellfireWall;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHellfireBarrier.class)
public abstract class EntityHellfireBarrierTweaksMixin {

    @Shadow(remap = false)
    public EntityHellfireWall[][] hellfireWalls;
    @Shadow(remap = false)
    public boolean wall;

    @Inject(
            method = "onUpdate",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/projectile/EntityHellfireWall;projectileLife:I", ordinal = 1, remap = false),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsEntityHellfireBarrier_onUpdate(CallbackInfo ci, @Local(ordinal = 0) int row, @Local(ordinal = 1) int col, @Local(ordinal = 0) double rotationRadians){
        if((this.wall && col < ForgeConfigHandler.server.rahovartConfig.hellfireWallDisplacement) ||
                (!this.wall && col < ForgeConfigHandler.server.rahovartConfig.hellfireBarrierDisplacement)){
            this.hellfireWalls[row][col].posX = this.hellfireWalls[row][col+1].posX;
            this.hellfireWalls[row][col].posZ = this.hellfireWalls[row][col+1].posZ;
        }
    }
}
