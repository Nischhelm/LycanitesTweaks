package lycanitestweaks.mixin.incontrol;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.compat.InControlCompat;
import mcjty.incontrol.rules.support.GenericRuleEvaluator;
import mcjty.tools.rules.CommonRuleEvaluator;
import mcjty.tools.rules.IModRuleCompatibilityLayer;
import mcjty.tools.typed.AttributeMap;
import net.minecraft.entity.Entity;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GenericRuleEvaluator.class)
public abstract class GenericRuleEvaluator_Mixin extends CommonRuleEvaluator {

    public GenericRuleEvaluator_Mixin(AttributeMap map, Logger logger, IModRuleCompatibilityLayer compatibility) {
        super(map, logger, compatibility);
    }

    @Inject(
            method = "addChecks",
            at = @At("TAIL"),
            remap = false
    )
    private void lycanitesTweaks_incontrolGenericRuleEvaluator_addChecks(AttributeMap map, CallbackInfo ci){
        if (map.has(InControlCompat.MIN_LEVEL)) this.lycanitesTweaks$addMinMobLevelCheck(map);
        if (map.has(InControlCompat.MAX_LEVEL)) this.lycanitesTweaks$addMaxMobLevelCheck(map);
        if (map.has(InControlCompat.IS_SUBSPECIES)) this.lycanitesTweaks$addSubspeciesCheck(map);
        if (map.has(InControlCompat.IS_MINION)) this.lycanitesTweaks$addminionCheck(map);
        if (map.has(InControlCompat.IS_UNCOMMON)) this.lycanitesTweaks$addUncommonCheck(map);
        if (map.has(InControlCompat.IS_RARE)) this.lycanitesTweaks$addRareCheck(map);
        if (map.has(InControlCompat.IS_SPAWNEDASBOSS)) this.lycanitesTweaks$addSpawnedAsBossCheck(map);
    }

    @Unique
    private void lycanitesTweaks$addMinMobLevelCheck(AttributeMap map) {
        int minLevel = map.get(InControlCompat.MIN_LEVEL);
        this.checks.add((event, query) -> {
            if(query.getEntity(event) instanceof BaseCreatureEntity){
                BaseCreatureEntity creature = (BaseCreatureEntity) query.getEntity(event);
                if(creature.firstSpawn) creature.onFirstSpawn();

                return creature.getLevel() >= minLevel;
            }
            return false;
        });
    }

    @Unique
    private void lycanitesTweaks$addMaxMobLevelCheck(AttributeMap map) {
        int maxLevel = map.get(InControlCompat.MAX_LEVEL);
        this.checks.add((event, query) -> {
            if(query.getEntity(event) instanceof BaseCreatureEntity){
                BaseCreatureEntity creature = (BaseCreatureEntity) query.getEntity(event);
                if(creature.firstSpawn) creature.onFirstSpawn();

                return creature.getLevel() <= maxLevel;
            }
            return false;
        });
    }

    @Unique
    private void lycanitesTweaks$addSubspeciesCheck(AttributeMap map) {
        int subspecies = map.get(InControlCompat.IS_SUBSPECIES);
        this.checks.add((event, query) -> {
            if(query.getEntity(event) instanceof BaseCreatureEntity){
                BaseCreatureEntity creature = (BaseCreatureEntity) query.getEntity(event);
                if(creature.firstSpawn) creature.onFirstSpawn();

                return creature.getSubspeciesIndex() == subspecies;
            }
            return false;
        });
    }

    @Unique
    private void lycanitesTweaks$addminionCheck(AttributeMap map) {
        boolean minion = map.get(InControlCompat.IS_MINION);
        this.checks.add((event, query) -> {
            if(query.getEntity(event) instanceof BaseCreatureEntity){
                return minion == ((BaseCreatureEntity) query.getEntity(event)).isMinion();
            }
            return false;
        });
    }

    @Unique
    private void lycanitesTweaks$addUncommonCheck(AttributeMap map) {
        boolean uncommon = map.get(InControlCompat.IS_UNCOMMON);
        this.checks.add((event, query) -> {
            if(query.getEntity(event) instanceof BaseCreatureEntity){
                BaseCreatureEntity creature = (BaseCreatureEntity) query.getEntity(event);
                if(creature.firstSpawn) creature.onFirstSpawn();

                return uncommon == (creature.getVariantIndex() != 0);
            }
            return false;
        });
    }

    @Unique
    private void lycanitesTweaks$addRareCheck(AttributeMap map) {
        boolean rare = map.get(InControlCompat.IS_RARE);
        this.checks.add((event, query) -> {
            if(query.getEntity(event) instanceof BaseCreatureEntity){
                BaseCreatureEntity creature = (BaseCreatureEntity) query.getEntity(event);
                if(creature.firstSpawn) creature.onFirstSpawn();

                return rare == creature.isRareVariant();
            }
            return false;
        });
    }

    @Unique
    private void lycanitesTweaks$addSpawnedAsBossCheck(AttributeMap map) {
        boolean spawnedAsBoss = map.get(InControlCompat.IS_SPAWNEDASBOSS);
        this.checks.add((event, query) -> {
            Entity entity = query.getEntity(event);
            return entity instanceof BaseCreatureEntity && (spawnedAsBoss == ((BaseCreatureEntity) entity).spawnedAsBoss);
        });
    }
}
