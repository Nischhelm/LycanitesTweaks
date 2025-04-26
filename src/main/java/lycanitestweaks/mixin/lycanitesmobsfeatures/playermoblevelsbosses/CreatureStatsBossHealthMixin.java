package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevelsbosses;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsBossHealthMixin {

    @Unique
    private final String HEALTH = "health";
    @Unique
    private final String DAMAGE = "damage";

    @Shadow(remap = false)
    public BaseCreatureEntity entity;

    @ModifyExpressionValue(
            method = "getLevelMultiplier",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/info/CreatureManager;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesCreatureStats_getLevelMultiplierBossHealth(double original, @Local(argsOnly = true) String stat){
        if((ForgeConfigHandler.featuresMixinConfig.bossInvertHealthDamageScale && stat.equals(DAMAGE)
                || !ForgeConfigHandler.featuresMixinConfig.bossInvertHealthDamageScale &&stat.equals(HEALTH))) {
            if (this.entity.isBossAlways()) {
                return original * ForgeConfigHandler.server.statsConfig.bossHealthBonusRatio;
            } else if (this.entity.isBoss()) {
                return original * ForgeConfigHandler.server.statsConfig.spawnedAsBossHealthBonusRatio;
            }
        }
        return original;
    }
}
