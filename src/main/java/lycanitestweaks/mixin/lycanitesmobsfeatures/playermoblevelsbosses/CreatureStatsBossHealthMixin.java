package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevelsbosses;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import com.lycanitesmobs.core.info.CreatureManager;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsBossHealthMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity entity;

    @ModifyExpressionValue(
            method = "getHealth",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/info/CreatureInfo;health:D"),
            remap = false
    )
    private double lycanitesTweaks_lycanitesCreatureStats_getHealthBossBase(double original){
        return CreatureManager.getInstance().creatureGroups.get("boss").hasEntity(this.entity) ?
                original * ForgeConfigHandler.server.statsConfig.bossHealthBonusRatio :
                original;
    }

    @ModifyReturnValue(
            method = "getHealth",
            at = @At("RETURN"),
            remap = false
    )
    private double lycanitesTweaks_lycanitesCreatureStats_getHealthBossReturn(double original){
        return CreatureManager.getInstance().creatureGroups.get("boss").hasEntity(this.entity) ?
                original + this.entity.creatureInfo.health * (1F - ForgeConfigHandler.server.statsConfig.bossHealthBonusRatio) :
                original;
    }
}
