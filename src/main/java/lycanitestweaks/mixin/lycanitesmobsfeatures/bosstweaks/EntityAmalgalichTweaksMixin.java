package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.block.BlockFireBase;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityAmalgalich;
import com.lycanitesmobs.core.entity.goals.actions.AttackRangedGoal;
import com.lycanitesmobs.core.entity.goals.actions.abilities.EffectAuraGoal;
import com.lycanitesmobs.core.entity.goals.actions.abilities.ForceGoal;
import lycanitestweaks.entity.goals.ExtendedGoalConditions;
import lycanitestweaks.entity.goals.actions.abilities.HealPortionWhenNoPlayersGoal;
import lycanitestweaks.entity.goals.actions.abilities.SummonLeveledMinionsGoal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.potion.PotionConsumed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAmalgalich.class)
public abstract class EntityAmalgalichTweaksMixin extends BaseCreatureEntity {

    // Changes with no configs
    /*
        Summon Banshee -> Not flight restricted, level match
        Summon Repear -> All phases
        Summon Geist -> More and minimum phase 2
        Summon Epion -> Minimum phase 2 and option to spawn single Crimson

        IDEAS
     */

    @Shadow(remap = false)
    private ForceGoal consumptionGoalP0;
    @Shadow(remap = false)
    private ForceGoal consumptionGoalP2;

    public EntityAmalgalichTweaksMixin(World world) {
        super(world);
    }

    @ModifyConstant(
            method = "initEntityAI",
            constant = @Constant(intValue = 400),
            remap = true
    )
    public int lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAICooldownSet(int constant){
        return ForgeConfigHandler.server.amalgalichConfig.consumptionGoalCooldown;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 8),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIConsptionAuraP0(EntityAIBase task) {
        if (ForgeConfigHandler.server.amalgalichConfig.consumptionEffect) {
            ((EffectAuraGoal) task).setEffect(PotionConsumed.INSTANCE);
        }
        if (ForgeConfigHandler.server.amalgalichConfig.consumptionAllPhases) {
            ((EffectAuraGoal) task).setConditions(new ExtendedGoalConditions().setBattlePhase(-1));
        }
        return task;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 13),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIConsptionAuraP2(EntityAIBase task) {
        if (ForgeConfigHandler.server.amalgalichConfig.consumptionEffect) {
            ((EffectAuraGoal) task).setEffect(PotionConsumed.INSTANCE);
        }
        if (ForgeConfigHandler.server.amalgalichConfig.consumptionAllPhases) {
            ((EffectAuraGoal) task).setConditions(new ExtendedGoalConditions().setBattlePhase(10));
        }
        return task;
    }

    @ModifyConstant(
            method = "initEntityAI",
            constant = @Constant(stringValue = "spectralbolt"),
            remap = true
    )
    public String lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIReplaceProjectile(String constant) {
        return ForgeConfigHandler.server.amalgalichConfig.mainProjectile;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 2),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIHeal(EntityAIBase task) {
        if(ForgeConfigHandler.server.amalgalichConfig.healPortionNoPlayers) return new HealPortionWhenNoPlayersGoal(this);
        else return task;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 3),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIBanshee(EntityAIBase task) {
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("banshee").setSummonRate(200).setPerPlayer(true);
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 7),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIReaper(EntityAIBase task) {
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("reaper").setSummonRate(100).setSummonCap(8).setPerPlayer(true);
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 10),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIGheist(EntityAIBase task) {
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("geist").setSummonRate(100).setSummonCap(8).setPerPlayer(true).setConditions(new ExtendedGoalConditions().setMinimumBattlePhase(1));
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 11),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIEpion(EntityAIBase task) {
        if (ForgeConfigHandler.server.amalgalichConfig.crimsonEpion)
            return (new SummonLeveledMinionsGoal(this)).setMinionInfo("epion").setSummonRate(600).setSummonCap(1).setVariantIndex(3).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(1));
        return (new SummonLeveledMinionsGoal(this)).setMinionInfo("epion").setSummonRate(100).setSummonCap(3).setPerPlayer(true).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(1));
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 14),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIDarkling(EntityAIBase task) {
        if (ForgeConfigHandler.server.amalgalichConfig.replaceLobDarkling)
            return (new SummonLeveledMinionsGoal(this)).setMinionInfo("darkling").setSummonRate(40).setSummonCap(9).setPerPlayer(true).setSizeScale(1.5).setConditions(new ExtendedGoalConditions().setMinimumBattlePhase(2));
        return task;
    }

    @Inject(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;initEntityAI()V"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsEntityAmalgalich_initEntityAIAdditionalGoals(CallbackInfo ci, @Local int consumptionGoalCooldown) {
        if (ForgeConfigHandler.server.amalgalichConfig.targetedAttack)
            this.tasks.addTask(this.nextCombatGoalIndex++, new AttackRangedGoal(this).setSpeed(1.0D).
                    setStaminaTime(ForgeConfigHandler.server.amalgalichConfig.targetedProjectileGoalCooldown).
                    setStaminaDrainRate(ForgeConfigHandler.server.amalgalichConfig.targetedProjectileStaminaDrainRate).
                    setRange(90.0F).setChaseTime(0).setCheckSight(false));
        if (ForgeConfigHandler.server.amalgalichConfig.grueSummon)
            this.tasks.addTask(this.nextIdleGoalIndex, (new SummonLeveledMinionsGoal(this)).setMinionInfo("grue").setSummonRate(600).setSummonCap(1).setVariantIndex(3).setSizeScale(2).setConditions((new ExtendedGoalConditions()).setMinimumBattlePhase(2)));

        if (ForgeConfigHandler.server.amalgalichConfig.consumptionAllPhases) {
            this.consumptionGoalP0.setPhase(-1);
            this.consumptionGoalP2.setPhase(10);
        }
    }

    // Don't play attack sound, same sfx consumption lol
    // Based on Asmodeus
    @Inject(
            method = "attackRanged",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityAmalgalich_attackRanged(Entity target, float range, CallbackInfo ci) {
        if (ForgeConfigHandler.server.amalgalichConfig.targetedAttack) {
            for (int i = 0; i < 5; i++) {
                this.fireProjectile(ForgeConfigHandler.server.amalgalichConfig.targetedProjectile, target, range, 0.0F, new Vec3d(0.0F, (double) this.getEyeHeight() * 0.7D, 0.0F), 0.1F, 4.0F, 2.0F);
            }
            if (!this.isBlocking() || this.canAttackWhileBlocking()) {
                this.triggerAttackCooldown();
            }
            ci.cancel();
        }
    }

    @ModifyExpressionValue(
            method = "extraAnimation01",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAmalgalich;getBattlePhase()I", ordinal = 0),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAmalgalich_extraAnimation01(int original) {
        if (ForgeConfigHandler.server.amalgalichConfig.consumptionAllPhases) return 0;
        return original;
    }

    @Inject(
            method = "onTryToDamageMinion",
            at = @At("TAIL"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityAmalgalich_onTryToDamageMinion(EntityLivingBase minion, float damageAmount, CallbackInfo ci) {
        if (ForgeConfigHandler.server.amalgalichConfig.consumptionDamageMaxHP) {
            minion.setDead();
            if (ForgeConfigHandler.server.amalgalichConfig.consumptionKillHeal > 0) {
                this.heal(ForgeConfigHandler.server.amalgalichConfig.consumptionKillHeal * this.getMaxHealth());
            } else
                this.heal(25.0F);
        }
    }

    @ModifyConstant(
            method = "onMinionDeath",
            constant = @Constant(intValue = 10),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsEntityAmalgalich_onMinionDeath(int constant, @Local(argsOnly = true) EntityLivingBase minion, @Local(argsOnly = true) DamageSource damageSource) {
        if (ForgeConfigHandler.server.amalgalichConfig.consumptionKillEpionChance < 1.0F) {
            if (damageSource.getTrueSource() instanceof EntityAmalgalich && this.getRNG().nextFloat() < ForgeConfigHandler.server.amalgalichConfig.consumptionKillEpionChance)
                return 0;
        }
        return ForgeConfigHandler.server.amalgalichConfig.customEpionExtinguishWidth;
    }

    // Stop the stupid float above fire and swim in water
    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityAmalgalich_onLivingUpdate(boolean original, @Local BlockPos arenaPos) {
        return original || this.world.getBlockState(arenaPos).getBlock().isPassable(this.world, arenaPos);
    }

    @Unique
    @Override
    public boolean canEntityBeSeen(Entity target) {
        if(ForgeConfigHandler.server.amalgalichConfig.playerXrayTarget && target instanceof EntityPlayer) return true;
        return super.canEntityBeSeen(target);
    }

    // Thanks Iqury
    @Unique
    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.getEntityWorld().isRemote) {
            int extinguishWidth = 8;
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
