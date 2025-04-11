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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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
        Heal 50 -> Heal 1%
        Summon Grigori -> Not flight restricted, level match
        Summon Trite -> Summon more
        Replaced -> Grell method summon to SummonGoal, Asmodeus wasn't using the saved grells for anything
        Replaced -> Hell Shield invulnerability with interactive shield hp
        Added -> Void Trite Minimum Phase 2
        Added -> Summon Phosphorescent Chupacabra Minimum Phase 3

        IDEAS
     */

    @Unique
    public double lycanitesTweaks$hellShieldHealth = 0D;
    @Unique
    private final int lycanitesTweaks$hellShieldHealthMax = ForgeConfigHandler.server.asmodeusConfig.hellShieldHealthMax;
    @Unique
    public double lycanitesTweaks$hellShieldOverHeal = ForgeConfigHandler.server.asmodeusConfig.hellShieldOverhealRatio;
    @Unique
    public double lycanitesTweaks$hellShieldRegen = ForgeConfigHandler.server.asmodeusConfig.hellShieldAstarothRegen;

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
        return (new HealPortionWhenNoPlayersGoal(this).setCheckRange(80));
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
        lycanitesTweaks$regenerateShield(0.5);
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
        lycanitesTweaks$regenerateShield(0.25);
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
        if(ForgeConfigHandler.server.asmodeusConfig.devilstarStreamAllPhases && this.updateTick % 20L != 0L) return 0;
        return original;
    }

    @Inject(
            method = "updatePhases",
            at = @At("HEAD"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityAsmodeus_updatePhasesHellShieldRegen(CallbackInfo ci){
        if (!this.astarothMinions.isEmpty() && this.updateTick % 20L == 0L) {
            for(EntityAstaroth minion : astarothMinions){
                if(minion.isEntityAlive()) this.lycanitesTweaks$regenerateShield(lycanitesTweaks$hellShieldRegen);
            }
        }
    }

    @ModifyExpressionValue(
            method = "isBlocking",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;getBattlePhase()I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAsmodeus_isBlockingAnyPhase(int original){
        return 1;
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
    public void onDamage(DamageSource damageSrc, float damage) {
        if(this.lycanitesTweaks$hellShieldHealth >= 0 && damageSrc.getTrueSource() instanceof EntityLivingBase){
            double shieldDamage = ((EntityLivingBase) damageSrc.getTrueSource()).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            shieldDamage = Math.pow(shieldDamage, ForgeConfigHandler.server.asmodeusConfig.hellShieldDamagePower);

            lycanitesTweaks$hellShieldHealth -= shieldDamage;
            lycanitesTweaks$hellShieldHealth = Math.max(0, lycanitesTweaks$hellShieldHealth);
        }
        super.onDamage(damageSrc, damage);
    }

    @Unique
    @Override
    public float getDamageModifier(DamageSource damageSrc) {
        if(this.isBlocking()) {
            return Math.max(0.01F, 1 - (float)(this.lycanitesTweaks$hellShieldHealth / this.lycanitesTweaks$hellShieldHealthMax));
        }
        return super.getDamageModifier(damageSrc);
    }

    @Unique
    public void lycanitesTweaks$regenerateShield(double modifier){
        lycanitesTweaks$hellShieldHealth += Math.min(lycanitesTweaks$hellShieldHealthMax * lycanitesTweaks$hellShieldOverHeal - lycanitesTweaks$hellShieldHealth,
                lycanitesTweaks$hellShieldHealthMax * modifier);
    }
}
