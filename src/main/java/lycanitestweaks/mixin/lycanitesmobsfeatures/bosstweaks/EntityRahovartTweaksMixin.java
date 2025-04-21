package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.block.BlockFireBase;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityRahovart;
import com.lycanitesmobs.core.entity.goals.actions.abilities.SummonMinionsGoal;
import lycanitestweaks.entity.goals.ExtendedGoalConditions;
import lycanitestweaks.entity.goals.actions.abilities.HealPortionWhenNoPlayersGoal;
import lycanitestweaks.entity.goals.actions.abilities.SummonLeveledMinionsGoal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRahovart.class)
public abstract class EntityRahovartTweaksMixin extends BaseCreatureEntity {

    // Changes with no configs
    /*
        Summon Wraith -> Not flight restricted, level match
        Summon Archvile -> Option Summon Royal Variant or three normal Minimum Phase 2
        Added -> Summon Ebon Cacodemon Minimum Phase 3

        IDEAS
        Barrier/Wall config spawn on phase start
        Barrier count config
        Cap Minion Count
     */

    @Shadow(remap = false)
    public int hellfireEnergy;

    public EntityRahovartTweaksMixin(World world) {
        super(world);
    }


    @ModifyConstant(
            method = "initEntityAI",
            constant = @Constant(stringValue = "hellfireball", ordinal = 0),
            remap = true
    )
    public String lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIReplaceProjectileAll(String constant){
        return ForgeConfigHandler.server.rahovartConfig.mainProjectileAll;
    }
    @ModifyConstant(
            method = "initEntityAI",
            constant = @Constant(stringValue = "hellfireball", ordinal = 1),
            remap = true
    )
    public String lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIReplaceProjectileTargeted(String constant){
        return ForgeConfigHandler.server.rahovartConfig.mainProjectileTarget;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 2),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityRahovart_initEntityAIHeal(EntityAIBase task){
        if(ForgeConfigHandler.server.rahovartConfig.healPortionNoPlayers) return new HealPortionWhenNoPlayersGoal(this).setCheckRange(48);
        else return task;
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
        if(ForgeConfigHandler.server.rahovartConfig.royalArchvile)
            return (new SummonLeveledMinionsGoal(this)).setMinionInfo("archvile").setSummonRate(600).setSummonCap(1).setVariantIndex(3).setSizeScale(2).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(1));
        return (new SummonMinionsGoal(this)).setMinionInfo("archvile").setSummonRate(200).setSummonCap(3).setPerPlayer(true).setSizeScale((double)2.0F).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(1));
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

    @ModifyConstant(
            method = "updatePhases",
            constant = @Constant(intValue = 20, ordinal = 0),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityRahovart_updatePhasesBelphEnergy(int constant){
        return ForgeConfigHandler.server.rahovartConfig.hellfireEnergyBelph;
    }

    @ModifyConstant(
            method = "updatePhases",
            constant = @Constant(intValue = 20, ordinal = 1),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityRahovart_updatePhasesBehemothEnergy(int constant){
        return ForgeConfigHandler.server.rahovartConfig.hellfireEnergyBehemoth;
    }

    @Inject(
            method = "updatePhases",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityRahovart;hellfireEnergy:I", ordinal = 2),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityRahovart_updatePhasesSelfEnergyP1(CallbackInfo ci){
        if (this.updateTick % 20L == 0L && this.hellfireEnergy < 100)
            this.hellfireEnergy = Math.min(99, this.hellfireEnergy + ForgeConfigHandler.server.rahovartConfig.hellfireEnergySelfP1);
    }

    @Inject(
            method = "updatePhases",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityRahovart;hellfireEnergy:I", ordinal = 6),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityRahovart_updatePhasesSelfEnergyP2(CallbackInfo ci){
        if (this.updateTick % 20L == 0L && this.hellfireEnergy < 100)
            this.hellfireEnergy = Math.min(99, this.hellfireEnergy + ForgeConfigHandler.server.rahovartConfig.hellfireEnergySelfP2);
    }

    @ModifyConstant(
            method = "updatePhases",
            constant = @Constant(intValue = 5, ordinal = 2),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityRahovart_updatePhasesSelfEnergyP3(int constant){
        return ForgeConfigHandler.server.rahovartConfig.hellfireEnergySelfP3;
    }

    @ModifyExpressionValue(
            method = "hellfireWallAttack",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityRahovart;hellfireWallTimeMax:I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityRahovart_hellfireWallAttackTimeMax(int original){
        return ForgeConfigHandler.server.rahovartConfig.hellfireWallTimeMax;
    }

    @Inject(
            method = "hellfireWallCleanup",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/projectile/EntityHellfireBarrier;setDead()V"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsEntityRahovart_hellfireWallCleanupRefund(CallbackInfo ci){
        this.hellfireEnergy += ForgeConfigHandler.server.rahovartConfig.hellfireWallCleanupRefund;
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

    @Inject(
            method = "hellfireBarrierCleanup",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/projectile/EntityHellfireBarrier;setDead()V"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsEntityRahovart_hellfireBarrierCleanupRefund(CallbackInfo ci){
        this.hellfireEnergy += ForgeConfigHandler.server.rahovartConfig.hellfireBarrierCleanupRefund;
    }

    // Stop the stupid float above fire and swim in water
    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityRahovart_onLivingUpdate(boolean original, @Local BlockPos arenaPos){
        return original || this.world.getBlockState(arenaPos).getBlock().isPassable(this.world, arenaPos);
    }

    @Unique
    @Override
    public boolean canEntityBeSeen(Entity target) {
        if(ForgeConfigHandler.server.rahovartConfig.playerXrayTarget && target instanceof EntityPlayer) return true;
        return super.canEntityBeSeen(target);
    }

    // Thanks Iqury
    @Unique
    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.getEntityWorld().isRemote) {
            int extinguishWidth = 3;
            int extinguishHeight = 2;
            for(int x = (int)this.posX - extinguishWidth; x <= (int)this.posX + extinguishWidth; ++x) {
                for(int y = (int)this.posY - extinguishHeight; y <= (int)this.posY + 2; ++y) {
                    for(int z = (int)this.posZ - extinguishWidth; z <= (int)this.posZ + extinguishWidth; ++z) {
                        Block block = this.getEntityWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
                        if (block instanceof BlockFireBase || block instanceof BlockFire) {
                            BlockPos placePos = new BlockPos(x, y, z);
                            this.getEntityWorld().setBlockToAir(placePos);
                        }
                    }
                }
            }
        }
    }
}
