package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityAsmodeus;
import com.lycanitesmobs.core.entity.creature.EntityAstaroth;
import com.lycanitesmobs.core.entity.goals.actions.abilities.SummonMinionsGoal;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.entity.goals.actions.abilities.HealPortionWhenNoPlayersGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
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
        Summon Wraith -> Not flight restricted, level match
        Summon Archvile -> Summon Royal Variant Minimum Phase 2
        Added -> Summon Ebon Cacodemon Minimum Phase 3

        IDEAS
        Wave/Wall config spawn on phase start
     */

    @Unique
    private final int lycanitesTweaks$hellShieldHealthMax = 10000;
    @Unique
    public double lycanitesTweaks$hellShieldHealth = 0D;
    @Unique
    public double lycanitesTweaks$hellShieldOverHeal = 1.5D;
    @Unique
    public double lycanitesTweaks$hellShieldRegen = 0.1D;

    @Shadow(remap = false)
    public List<EntityAstaroth> astarothMinions;

    public EntityAsmodeusTweaksMixin(World world) {
        super(world);
    }

    @ModifyReceiver(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/goals/actions/abilities/SummonMinionsGoal;setAntiFlight(Z)Lcom/lycanitesmobs/core/entity/goals/actions/abilities/SummonMinionsGoal;", remap = false),
            remap = true
    )
    public SummonMinionsGoal lycanitesTweaks_lycanitesMobsEntityAsmodeus_initEntityAIWraithGround(SummonMinionsGoal instance, boolean antiFlight){
        return instance;
    }

    @ModifyArg(
            method = "initEntityAI",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/EntityAITasks;addTask(ILnet/minecraft/entity/ai/EntityAIBase;)V", ordinal = 2),
            index = 1,
            remap = true
    )
    public EntityAIBase lycanitesTweaks_lycanitesMobsEntityAsmodeus_initEntityAIHeal(EntityAIBase task){
        return (new HealPortionWhenNoPlayersGoal(this).setCheckRange(48));
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
        minion.damageMax = BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
        minion.damageLimit = (float)BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
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
        minion.damageMax = BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
        minion.damageLimit = (float)BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
        lycanitesTweaks$regenerateShield(0.25);
    }

    @ModifyConstant(
            method = "attackDevilstar",
            constant = @Constant(stringValue = "devilstar"),
            remap = false
    )
    public String eee(String constant){
        return "demonicblast";
    }

    @ModifyReceiver(
            method = "attackRanged",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;attackHitscan(Lnet/minecraft/entity/Entity;D)Z"),
            remap = false
    )
    public EntityAsmodeus fff(EntityAsmodeus instance, Entity entity, double v, @Local(argsOnly = true) Entity target, @Local(argsOnly = true) float range){
        super.attackRanged(target, range);
        return instance;
    }

    @ModifyExpressionValue(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;getBattlePhase()I", ordinal = 0),
            remap = false
    )
    public int ttt(int original){
        if(this.updateTick % 20L != 0L) return 0;
        return original;
    }

    @Inject(
            method = "updatePhases",
            at = @At("HEAD"),
            remap = false
    )
    public void eee(CallbackInfo ci){
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
    public int zzz(int original){
        return 1;
    }

    @ModifyExpressionValue(
            method = "isDamageTypeApplicable",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;isBlocking()Z"),
            remap = false
    )
    public boolean www(boolean original, @Local(argsOnly = true) DamageSource source, @Local(argsOnly = true) float damage){
        return false;
    }
    @Unique
    @Override
    public void onDamage(DamageSource damageSrc, float damage) {
        if(this.lycanitesTweaks$hellShieldHealth >= 0 && damageSrc.getTrueSource() instanceof EntityLivingBase){
            double shieldDamage = ((EntityLivingBase) damageSrc.getTrueSource()).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            lycanitesTweaks$damageShield(shieldDamage);
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
    public void lycanitesTweaks$damageShield(double damage){
        damage = damage * damage * damage;

        LycanitesTweaks.LOGGER.log(Level.INFO, "SHIELD DAMAGE TAKEN: " + damage);
        lycanitesTweaks$hellShieldHealth -= damage;
        lycanitesTweaks$hellShieldHealth = Math.max(0, lycanitesTweaks$hellShieldHealth);
    }

    @Unique
    public void lycanitesTweaks$regenerateShield(double modifier){
        lycanitesTweaks$hellShieldHealth += Math.min(lycanitesTweaks$hellShieldHealthMax * lycanitesTweaks$hellShieldOverHeal - lycanitesTweaks$hellShieldHealth,
                lycanitesTweaks$hellShieldHealthMax * modifier);
        LycanitesTweaks.LOGGER.log(Level.INFO, "SHIELD HP: " + lycanitesTweaks$hellShieldHealth);
    }
}
