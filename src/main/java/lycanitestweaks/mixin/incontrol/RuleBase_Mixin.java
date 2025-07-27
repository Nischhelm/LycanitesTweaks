package lycanitestweaks.mixin.incontrol;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.compat.InControlCompat;
import mcjty.tools.rules.IModRuleCompatibilityLayer;
import mcjty.tools.rules.RuleBase;
import mcjty.tools.typed.AttributeMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(RuleBase.class)
public abstract class RuleBase_Mixin<T extends RuleBase.EventGetter> {

    @Shadow(remap = false)
    @Final protected List<Consumer<T>> actions;

    @Inject(
            method = "addActions",
            at = @At("TAIL"),
            remap = false
    )
    private void lycanitesTweaks_incontrolRuleBase_addActions(AttributeMap map, IModRuleCompatibilityLayer layer, CallbackInfo ci) {
        if (map.has(InControlCompat.SET_LEVEL)) this.lycanitesTweaks$addSetMobLevelAction(map);
        if (map.has(InControlCompat.ADD_LEVEL)) this.lycanitesTweaks$addAddMobLevelAction(map);
        if (map.has(InControlCompat.SET_SUBSPECIES)) this.lycanitesTweaks$addSetSubspeciesAction(map);
        if (map.has(InControlCompat.SET_VARIANT)) this.lycanitesTweaks$addSetVariantAction(map);
        if (map.has(InControlCompat.SET_SPAWNEDASBOSS)) this.lycanitesTweaks$addSetSpawnedAsBossAction(map);
        if (map.has(InControlCompat.SET_BOSSDAMAGELIMIT)) this.lycanitesTweaks$addSetBossDamageLimitAction(map);
    }

    @Unique
    private void lycanitesTweaks$addAddMobLevelAction(AttributeMap map) {
        int addLevel = map.get(InControlCompat.ADD_LEVEL);
        if (addLevel > 0) {
            actions.add(event -> {
                if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                    BaseCreatureEntity creature = ((BaseCreatureEntity) event.getEntityLiving());
                    creature.onFirstSpawn();
                    creature.addLevel(addLevel);
                }
            });
        }
    }

    @Unique
    private void lycanitesTweaks$addSetMobLevelAction(AttributeMap map) {
        int setLevel = map.get(InControlCompat.SET_LEVEL);
        if (setLevel > 0) {
            actions.add(event -> {
                if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                    BaseCreatureEntity creature = ((BaseCreatureEntity) event.getEntityLiving());
                    creature.applyLevel(setLevel);
                }
            });
        }
    }

    @Unique
    private void lycanitesTweaks$addSetSubspeciesAction(AttributeMap map) {
        int subspecies = map.get(InControlCompat.SET_SUBSPECIES);
        actions.add(event -> {
            if(event.getEntityLiving() instanceof BaseCreatureEntity)
                ((BaseCreatureEntity) event.getEntityLiving()).setSubspecies(subspecies);
        });
    }

    @Unique
    private void lycanitesTweaks$addSetVariantAction(AttributeMap map) {
        int variant = map.get(InControlCompat.SET_VARIANT);
        actions.add(event -> {
            if(event.getEntityLiving() instanceof BaseCreatureEntity)
                ((BaseCreatureEntity) event.getEntityLiving()).applyVariant(variant);
        });
    }

    @Unique
    private void lycanitesTweaks$addSetSpawnedAsBossAction(AttributeMap map) {
        if (map.get(InControlCompat.SET_SPAWNEDASBOSS)) {
            actions.add(event -> {
                if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                    BaseCreatureEntity creature = ((BaseCreatureEntity) event.getEntityLiving());
                    creature.spawnedAsBoss = true;
                }
            });
        }
    }

    @Unique
    private void lycanitesTweaks$addSetBossDamageLimitAction(AttributeMap map) {
        if (map.get(InControlCompat.SET_BOSSDAMAGELIMIT)) {
            actions.add(event -> {
                if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                    BaseCreatureEntity creature = ((BaseCreatureEntity) event.getEntityLiving());
                    creature.damageMax = BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
                    creature.damageLimit = (float) BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
                }
            });
        }
    }
}
