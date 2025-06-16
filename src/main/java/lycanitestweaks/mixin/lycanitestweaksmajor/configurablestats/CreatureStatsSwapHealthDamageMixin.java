package lycanitestweaks.mixin.lycanitestweaksmajor.configurablestats;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsSwapHealthDamageMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity entity;

    @Unique
    private boolean lycanitesTweaks$shouldDoSwap(){
        if(this.entity.isBossAlways() && ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.swapHealthDamageMainBoss) return true;
        else if(this.entity.spawnedAsBoss && ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.swapHealthDamageSpawnedAsBoss) return true;
        else if(!this.entity.isTamed() && this.entity.isMinion() && ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.swapHealthDamageHostileMinion) return true;
        else if(this.entity.isTamed() && ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.swapHealthDamageTamed) return true;
        return false;
    }

    @ModifyArg(
            method = "getHealth",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/CreatureStats;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    private String lycanitesTweaks_lycanitesCreatureStats_getHealthBossInvert(String stat){
        if(this.lycanitesTweaks$shouldDoSwap()) return "damage";
        return stat;
    }

    @ModifyArg(
            method = "getDamage",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/CreatureStats;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    private String lycanitesTweaks_lycanitesCreatureStats_getDamageBossInvert(String stat){
        if(this.lycanitesTweaks$shouldDoSwap()) return "health";
        return stat;
    }
}
