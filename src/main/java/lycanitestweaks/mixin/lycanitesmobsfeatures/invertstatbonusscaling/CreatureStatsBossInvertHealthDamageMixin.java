package lycanitestweaks.mixin.lycanitesmobsfeatures.invertstatbonusscaling;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import com.lycanitesmobs.core.info.CreatureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsBossInvertHealthDamageMixin {

    @Unique
    private final String HEALTH = "health";
    @Unique
    private final String DAMAGE = "damage";

    @Shadow(remap = false)
    public BaseCreatureEntity entity;

    @ModifyArg(
            method = "getHealth",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/CreatureStats;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    private String lycanitesTweaks_lycanitesCreatureStats_getHealthBossInvert(String stat){
        if(CreatureManager.getInstance().creatureGroups.get("boss").hasEntity(this.entity)) return DAMAGE;
        return stat;
    }

    @ModifyArg(
            method = "getDamage",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/CreatureStats;getLevelMultiplier(Ljava/lang/String;)D"),
            remap = false
    )
    private String lycanitesTweaks_lycanitesCreatureStats_getDamageBossInvert(String stat){
        if(CreatureManager.getInstance().creatureGroups.get("boss").hasEntity(this.entity)) return HEALTH;
        return stat;
    }
}
