package lycanitestweaks.mixin.lycanitestweaksmajor.configurablestats;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsCapMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity entity;
    @Shadow(remap = false)
    protected abstract double getVariantMultiplier(String stat);

    @ModifyReturnValue(
            method = "getDefense",
            at = @At("RETURN"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesCreatureStats_getDefense(double original, @Local String statName){
        if(ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capDefenseRatio == 0) return original;
        return Math.min(original, ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capDefenseRatio * this.entity.creatureInfo.defense * this.getVariantMultiplier(statName));
    }

    @ModifyReturnValue(
            method = "getSpeed",
            at = @At("RETURN"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesCreatureStats_getSpeed(double original, @Local String statName){
        if(ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capSpeedRatio == 0) return original;
        return Math.min(original, ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capSpeedRatio * this.entity.creatureInfo.speed * this.getVariantMultiplier(statName) * 0.01D);
    }

    @ModifyReturnValue(
            method = "getEffect",
            at = @At("RETURN"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesCreatureStats_getEffect(double original, @Local String statName){
        if(ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capEffectDurationRatio == 0) return original;
        return Math.min(original, ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capEffectDurationRatio * this.entity.creatureInfo.effectDuration * this.getVariantMultiplier(statName));
    }

    @ModifyReturnValue(
            method = "getPierce",
            at = @At("RETURN"),
            remap = false
    )
    public double lycanitesTweaks_lycanitesCreatureStats_getPierce(double original, @Local String statName){
        if(ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capPierceRatio == 0) return original;
        return Math.min(original, ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capPierceRatio * this.entity.creatureInfo.pierce * this.getVariantMultiplier(statName));
    }
}
