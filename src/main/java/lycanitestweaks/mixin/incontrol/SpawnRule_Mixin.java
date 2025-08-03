package lycanitestweaks.mixin.incontrol;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.compat.InControlCompat;
import mcjty.incontrol.rules.SpawnRule;
import mcjty.tools.rules.IModRuleCompatibilityLayer;
import mcjty.tools.rules.RuleBase;
import mcjty.tools.typed.Attribute;
import mcjty.tools.typed.AttributeMap;
import mcjty.tools.typed.GenericAttributeMapFactory;
import net.minecraft.world.storage.loot.RandomValueRange;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnRule.class)
public abstract class SpawnRule_Mixin extends RuleBase<RuleBase.EventGetter>  {

    public SpawnRule_Mixin(Logger logger) {
        super(logger);
    }

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lmcjty/tools/typed/GenericAttributeMapFactory;attribute(Lmcjty/tools/typed/Attribute;)Lmcjty/tools/typed/GenericAttributeMapFactory;", ordinal = 0, remap = false)
    )
    private static GenericAttributeMapFactory lycanitesTweaks_incontrolSpawnRule_addCustomRules(GenericAttributeMapFactory original){
        return original
                .attribute(Attribute.create(InControlCompat.ADD_LEVEL))
                .attribute(Attribute.create(InControlCompat.ADD_LEVEL_RAND_MIN))
                .attribute(Attribute.create(InControlCompat.SET_LEVEL))
                .attribute(Attribute.create(InControlCompat.SET_SUBSPECIES))
                .attribute(Attribute.create(InControlCompat.SET_VARIANT))
                .attribute(Attribute.create(InControlCompat.SET_SPAWNEDASBOSS))
                .attribute(Attribute.create(InControlCompat.SET_BOSSDAMAGELIMIT));
    }

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
    private void lycanitesTweaks$addAddMobLevelAction(AttributeMap map) {
        int addLevel = map.get(InControlCompat.ADD_LEVEL);
        if (addLevel > 0) {
            actions.add(event -> {
                if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                    BaseCreatureEntity creature = ((BaseCreatureEntity) event.getEntityLiving());
                    creature.onFirstSpawn();
                    if(map.has(InControlCompat.ADD_LEVEL_RAND_MIN)){
                        creature.addLevel(
                                new RandomValueRange(
                                        map.get(InControlCompat.ADD_LEVEL_RAND_MIN),
                                        map.get(InControlCompat.ADD_LEVEL))
                                        .generateInt(creature.getRNG())
                        );
                    }
                    else creature.addLevel(addLevel);
                }
            });
        }
    }

    @Unique
    private void lycanitesTweaks$addSetSubspeciesAction(AttributeMap map) {
        int subspecies = map.get(InControlCompat.SET_SUBSPECIES);
        actions.add(event -> {
            if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                ((BaseCreatureEntity) event.getEntityLiving()).setSubspecies(subspecies);
                ((BaseCreatureEntity) event.getEntityLiving()).firstSpawn = false;
            }
        });
    }

    @Unique
    private void lycanitesTweaks$addSetVariantAction(AttributeMap map) {
        int variant = map.get(InControlCompat.SET_VARIANT);
        actions.add(event -> {
            if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                ((BaseCreatureEntity) event.getEntityLiving()).applyVariant(variant);
                ((BaseCreatureEntity) event.getEntityLiving()).firstSpawn = false;
            }
        });
    }

    @Unique
    private void lycanitesTweaks$addSetSpawnedAsBossAction(AttributeMap map) {
        if (map.get(InControlCompat.SET_SPAWNEDASBOSS)) {
            actions.add(event -> {
                if(event.getEntityLiving() instanceof BaseCreatureEntity) {
                    BaseCreatureEntity creature = ((BaseCreatureEntity) event.getEntityLiving());
                    creature.spawnedAsBoss = true;
                    creature.refreshAttributes();
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
