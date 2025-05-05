package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityAsmodeus;
import com.lycanitesmobs.core.entity.creature.EntityAstaroth;
import com.lycanitesmobs.core.entity.goals.GoalConditions;
import com.lycanitesmobs.core.entity.goals.actions.abilities.FireProjectilesGoal;
import com.lycanitesmobs.core.entity.navigate.ArenaNode;
import lycanitestweaks.entity.goals.ExtendedGoalConditions;
import lycanitestweaks.entity.goals.actions.abilities.HealPortionWhenNoPlayersGoal;
import lycanitestweaks.entity.goals.actions.abilities.SummonLeveledMinionsGoal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.Entity;
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

import java.util.List;

@Mixin(EntityAsmodeus.class)
public abstract class EntityAsmodeusTweaksMixin extends BaseCreatureEntity {

    // Changes with no configs
    /*
        Summon Grigori -> Not flight restricted, level match
        Summon Trite -> Summon more
        Replaced -> Grell method summon to SummonGoal, Asmodeus wasn't using the saved grells for anything
        Replaced -> Hell Shield invulnerability with interactive shield hp
        Added -> Void Trite Minimum Phase 2
        Added -> Summon Phosphorescent Chupacabra Minimum Phase 3

        IDEAS
     */

    @Shadow(remap = false)
    public List<EntityAstaroth> astarothMinions;
    @Shadow(remap = false)
    public ArenaNode currentArenaNode;

    public EntityAsmodeusTweaksMixin(World world) {
        super(world);
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 2),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAsmodeus_initEntityAIHeal(EntityAIBase task){
        if(ForgeConfigHandler.server.asmodeusConfig.healPortionNoPlayers) return new HealPortionWhenNoPlayersGoal(this).setCheckRange(80);
        else return task;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 3),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAsmodeus_initEntityAIGrigori(EntityAIBase task){
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("grigori").setSummonRate(200).setPerPlayer(true);
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 5),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAsmodeus_initEntityAITriteNormal(EntityAIBase task){
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("trite").setSummonRate(60).setSummonCap(6).setPerPlayer(true).setConditions((new GoalConditions()).setBattlePhase(0));
    }

    @Inject(
            method = "initEntityAI",
            at = @At(value = "HEAD"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsEntityAsmodeus_initEntityAIAdditionalGoals(CallbackInfo ci){
        this.tasks.addTask(this.nextIdleGoalIndex, (new SummonLeveledMinionsGoal(this)).setMinionInfo("grell").setSummonRate(200).setSummonCap(6).setPerPlayer(true).setConditions(new ExtendedGoalConditions().setMinimumBattlePhase(1)));
        this.tasks.addTask(this.nextIdleGoalIndex, (new SummonLeveledMinionsGoal(this)).setMinionInfo("trite").setSummonRate(40).setSummonCap(9).setPerPlayer(true).setSubSpeciesIndex(1).setConditions(new ExtendedGoalConditions().setMinimumBattlePhase(1)));
        if(ForgeConfigHandler.server.asmodeusConfig.chupacabraSummon)
            this.tasks.addTask(this.nextIdleGoalIndex, (new SummonLeveledMinionsGoal(this)).setMinionInfo("chupacabra").setSummonRate(600).setSummonCap(1).setVariantIndex(3).setSizeScale(2).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(2)));
        if(ForgeConfigHandler.server.asmodeusConfig.additionalProjectileAdd) {
            this.tasks.addTask(this.nextIdleGoalIndex, (new FireProjectilesGoal(this)).setProjectile(ForgeConfigHandler.server.asmodeusConfig.additionalProjectile).setFireRate(640).setVelocity(0.8F).setScale(8.0F).setAllPlayers(true));
            this.tasks.addTask(this.nextIdleGoalIndex, (new FireProjectilesGoal(this)).setProjectile(ForgeConfigHandler.server.asmodeusConfig.additionalProjectile).setFireRate(960).setVelocity(0.8F).setScale(8.0F));
        }
    }

    @ModifyExpressionValue(
            method = "updatePhases",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;devilstarStreamTimeMax:I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesDevilstarUpTime(int original){
        return ForgeConfigHandler.server.asmodeusConfig.devilstarStreamUpTime;
    }

    @ModifyExpressionValue(
            method = "updatePhases",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;devilstarStreamChargeMax:I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesDevilstarCooldown(int original){
        return ForgeConfigHandler.server.asmodeusConfig.devilstarCooldown;
    }

    @ModifyExpressionValue(
            method = "updatePhases",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;hellshieldAstarothRespawnTimeMax:I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesAstarothsRespawnP2(int original){
        return ForgeConfigHandler.server.asmodeusConfig.astarothsRespawnTimePhase2;
    }

    @ModifyExpressionValue(
            method = "updatePhases",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;rebuildAstarothRespawnTimeMax:I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesAstarothsRespawnP3(int original){
        return ForgeConfigHandler.server.asmodeusConfig.astarothsRespawnTimePhase3;
    }

    // Arachnotron
    @Inject(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesPhaseTwoMinion(CallbackInfo ci, @Local EntityAstaroth minion){
        minion.setCustomNameTag("Arachnotron");
        minion.setSizeScale(1.8);
        if(ForgeConfigHandler.server.asmodeusConfig.astarothsUseBossDamageLimit) {
            minion.damageMax = BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
            minion.damageLimit = (float) BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
            minion.spawnedAsBoss = true;
        }
        if(ForgeConfigHandler.server.asmodeusConfig.astarothsTeleportAdjacent && this.currentArenaNode != null){
            BlockPos randomPos = this.currentArenaNode.getRandomAdjacentNode().pos;
            minion.setPosition(randomPos.getX(), randomPos.getY(), randomPos.getZ());
        }
    }

    // Asakku
    @Inject(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesPhaseThreeMinion(CallbackInfo ci, @Local EntityAstaroth minion){
        minion.setCustomNameTag("Asakku");
        minion.setSizeScale(2.5);
        minion.setSubspecies(1);
        if(ForgeConfigHandler.server.asmodeusConfig.astarothsUseBossDamageLimit) {
            minion.damageMax = BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
            minion.damageLimit = (float) BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
            minion.spawnedAsBoss = true;
        }
        if(ForgeConfigHandler.server.asmodeusConfig.astarothsTeleportAdjacent && this.currentArenaNode != null){
            BlockPos randomPos = this.currentArenaNode.getRandomAdjacentNode().pos;
            minion.setPosition(randomPos.getX(), randomPos.getY(), randomPos.getZ());
        }
    }

    @ModifyConstant(
            method = "updatePhases",
            constant = @Constant(intValue = 6),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesRemoveGrell(int original){
        return 0;
    }

    @ModifyArg(
            method = "attackRanged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;<init>(DDD)V"),
            index = 1,
            remap = true
    )
    public double lycanitesTweaks_lycanitesMobsEntityAsmodeus_attackRangedDevilGatlingOffset(double yIn){
        return this.getEyeHeight() * 0.5F;
    }

    @WrapWithCondition(
            method = "attackRanged",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;attackHitscan(Lnet/minecraft/entity/Entity;D)Z"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityAsmodeus_attackRangedToggleHitscan(EntityAsmodeus instance, Entity entity, double v, @Local(argsOnly = true) Entity target, @Local(argsOnly = true) float range){
        if(ForgeConfigHandler.server.asmodeusConfig.disableRangedHitscan) {
            super.attackRanged(target, range);
            return false;
        }
        return true;
    }

    @ModifyConstant(
            method = "attackDevilstar",
            constant = @Constant(stringValue = "devilstar"),
            remap = false
    )
    public String lycanitesTweaks_lycanitesMobsEntityAsmodeus_attackDevilstarSetProjectile(String constant){
        return ForgeConfigHandler.server.asmodeusConfig.devilstarProjectile;
    }

    @ModifyExpressionValue(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;getBattlePhase()I", ordinal = 0),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesAnyPhaseDevilstar(int original){
        if(this.updateTick % 20L != 0L && ForgeConfigHandler.server.asmodeusConfig.devilstarStreamAllPhases) return 0;
        return original;
    }

    @Inject(
            method = "updatePhases",
            at = @At("TAIL"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesAnyPhaseRepair(CallbackInfo ci){
        if(this.updateTick % 20 == 0L && ForgeConfigHandler.server.asmodeusConfig.repairAllPhases){
            // Heal:
            if(!this.astarothMinions.isEmpty()) {
                float healAmount = this.astarothMinions.size();
                if (((this.getHealth() + healAmount) / this.getMaxHealth()) > 0.2D) {
                    float healValue = 2F;
                    if(ForgeConfigHandler.server.asmodeusConfig.repairHealingPortion > 0) healValue = ForgeConfigHandler.server.asmodeusConfig.repairHealingPortion * this.getMaxHealth();
                    this.heal(healAmount * healValue);
                }
            }
        }
    }

    @ModifyConstant(
            method = "updatePhases",
            constant = @Constant(floatValue = 2F),
            remap = false
    )
    public float lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesRepairAmount(float constant){
        if(ForgeConfigHandler.server.asmodeusConfig.repairHealingPortion > 0) return ForgeConfigHandler.server.asmodeusConfig.repairHealingPortion * this.getMaxHealth();
        return constant;
    }

    @ModifyExpressionValue(
            method = "isBlocking",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;getBattlePhase()I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_isBlockingAnyPhase(int original){
        if(ForgeConfigHandler.server.asmodeusConfig.hellshieldAllPhases)
            return 1;
        return original;
    }

    @ModifyExpressionValue(
            method = "isBlocking",
            at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityAsmodeus_isBlockingMinionAlive(boolean original){
        // compared value is inverted due to !
        if (!this.astarothMinions.isEmpty()) {
            for(EntityAstaroth minion : this.astarothMinions){
                if(minion.isEntityAlive()) return false;
            }
        }
        return true;
    }

    @ModifyExpressionValue(
            method = "isDamageTypeApplicable",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;isBlocking()Z"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityAsmodeus_isDamageTypeApplicableWhileBlocking(boolean original, @Local(argsOnly = true) DamageSource source, @Local(argsOnly = true) float damage){
        return false;
    }

    // Stop the stupid float above fire and swim in water
    @ModifyExpressionValue(
            method = "updateArenaMovement",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityAsmodeus_updateArenaMovement(boolean original, @Local BlockPos arenaPos){
        return original || this.world.getBlockState(arenaPos).getBlock().isPassable(this.world, arenaPos);
    }

    @Unique
    @Override
    public boolean canEntityBeSeen(Entity target) {
        if(ForgeConfigHandler.server.asmodeusConfig.playerXrayTarget && target instanceof EntityPlayer) return true;
        return super.canEntityBeSeen(target);
    }

    @Unique
    @Override
    public float getDamageModifier(DamageSource damageSrc) {
        if(this.isBlocking()) {
            return 1F - ForgeConfigHandler.server.asmodeusConfig.hellshieldDamageReduction;
        }
        return super.getDamageModifier(damageSrc);
    }
}
