package lycanitestweaks.mixin.lycanitestweaksmajor.configurablestats;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsBossHealthMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity entity;

    @ModifyExpressionValue(
            method = "getLevelMultiplier",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/info/CreatureManager;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesCreatureStats_getLevelMultiplierBossHealth(double original, @Local(argsOnly = true) String stat){
        if((ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.bossInvertHealthDamageScale && stat.equals("damage")
                || !ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.bossInvertHealthDamageScale &&stat.equals("health"))) {
            if (this.entity.isBossAlways()) {
                return original * ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.bossHealthBonusRatio;
            } else if (this.entity.isBoss()) {
                return (this.entity.isRareVariant()) ?
                        original * ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.spawnedAsBossRareHealthBonusRatio :
                        original * ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.spawnedAsBossHealthBonusRatio;
            }
        }
        return original;
    }
}
