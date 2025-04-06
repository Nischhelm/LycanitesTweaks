package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityRahovart;
import lycanitestweaks.entity.goals.ExtendedGoalConditions;
import lycanitestweaks.entity.goals.actions.abilities.HealPortionWhenNoPlayersGoal;
import lycanitestweaks.entity.goals.actions.abilities.SummonLeveledMinionsGoal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRahovart.class)
public abstract class EntityRahovartTweaksMixin extends BaseCreatureEntity {

    // Changes with no configs
    /*
        Heal 50 -> Heal 1%
        Summon Wraith -> Not flight restricted, level match
        Summon Archvile -> Summon Royal Variant Minimum Phase 2
        Added -> Summon Ebon Cacodemon Minimum Phase 3

        IDEAS
        Barrier/Wall config spawn on phase start
        Barrier count config
        Cap Minion Count
     */

    public EntityRahovartTweaksMixin(World world) {
        super(world);
    }

    @ModifyConstant(
            method = "initEntityAI",
            constant = @Constant(stringValue = "hellfireball"),
            remap = true
    )
    public String lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIReplaceProjectile(String constant){
        return ForgeConfigHandler.server.rahovartConfig.mainProjectile;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 2),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIHeal(EntityAIBase task){
        return (new HealPortionWhenNoPlayersGoal(this).setCheckRange(48));
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 3),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIWraith(EntityAIBase task){
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("wraith").setSummonRate(200).setPerPlayer(true);
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 6),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIArchville(EntityAIBase task){
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("archvile").setSummonRate(600).setSummonCap(1).setVariantIndex(3).setSizeScale(2).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(1));
    }

    @Inject(
            method = "initEntityAI",
            at = @At(value = "HEAD"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIAdditionalGoals(CallbackInfo ci){
        if(ForgeConfigHandler.server.rahovartConfig.cacodemonSummon)
            this.tasks.addTask(this.nextIdleGoalIndex, (new SummonLeveledMinionsGoal(this)).setMinionInfo("cacodemon").setSummonRate(600).setSummonCap(1).setVariantIndex(3).setSizeScale(2).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(2)));
    }

    @ModifyArg(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityRahovart;summonMinion(Lnet/minecraft/entity/EntityLivingBase;DD)V"),
            index = 0,
            remap = false
    )
    public EntityLivingBase lycanitesTweaks_lycanitesMobsEntityRahovart_updatePhasesSetTemporaryMinions(EntityLivingBase creature){
        // Skip BaseCreatureCheck, we know they are
        if(ForgeConfigHandler.server.rahovartConfig.minionTemporaryDuration > 0)
            ((BaseCreatureEntity)creature).setTemporary(ForgeConfigHandler.server.rahovartConfig.minionTemporaryDuration);
        return creature;
    }

    @ModifyArg(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityRahovart;summonMinion(Lnet/minecraft/entity/EntityLivingBase;DD)V"),
            index = 2,
            remap = false
    )
    public double lycanitesTweaks_lycanitesMobsEntityRahovart_updatePhasesMinionSpawnRange(double range){
        int min = ForgeConfigHandler.server.rahovartConfig.minionSpawnRangeMin;
        int max = ForgeConfigHandler.server.rahovartConfig.minionSpawnRangeMax;
        if(min > max) return range;
        return (this.getRNG().nextFloat() * (max - min)) + min;
    }

    @ModifyExpressionValue(
            method = "hellfireWallAttack",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityRahovart;hellfireWallTimeMax:I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityRahovart_hellfireWallAttackTimeMax(int original){
        return ForgeConfigHandler.server.rahovartConfig.hellfireWallTimeMax;
    }

    @ModifyConstant(
            method = "onMinionDeath",
            constant = @Constant(intValue = 100),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityRahovart_onMinionDeathBehemothBarrier(int constant){
        return ForgeConfigHandler.server.rahovartConfig.hellfireBarrierBehemothDegrade;
    }

    @ModifyConstant(
            method = "onMinionDeath",
            constant = @Constant(intValue = 50),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityRahovart_onMinionDeathBelphBarrier(int constant){
        return ForgeConfigHandler.server.rahovartConfig.hellfireBarrierBelphDegrade;
    }
}
