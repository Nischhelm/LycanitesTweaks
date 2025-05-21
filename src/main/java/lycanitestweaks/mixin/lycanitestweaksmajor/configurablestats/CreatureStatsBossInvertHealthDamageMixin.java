package lycanitestweaks.mixin.lycanitestweaksmajor.configurablestats;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsBossInvertHealthDamageMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity entity;

    @ModifyArg(
            method = "getHealth",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/CreatureStats;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    private String lycanitesTweaks_lycanitesCreatureStats_getHealthBossInvert(String stat){
        if(this.entity.isBossAlways() || (ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.spawnedAsBossInvert && this.entity.isBoss()))
            return "damage";
        return stat;
    }

    @ModifyArg(
            method = "getDamage",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/CreatureStats;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    private String lycanitesTweaks_lycanitesCreatureStats_getDamageBossInvert(String stat){
        if(this.entity.isBossAlways() || (ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.spawnedAsBossInvert && this.entity.isBoss()))
            return "health";
        return stat;
    }
}
