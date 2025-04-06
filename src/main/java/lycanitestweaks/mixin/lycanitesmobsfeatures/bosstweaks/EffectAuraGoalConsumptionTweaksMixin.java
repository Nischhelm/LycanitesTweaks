package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.creature.EntityAmalgalich;
import com.lycanitesmobs.core.entity.goals.actions.abilities.EffectAuraGoal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EffectAuraGoal.class)
public abstract class EffectAuraGoalConsumptionTweaksMixin {

    @WrapWithCondition(
            method = "updateTask",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsEffectAuraGoal_updateTaskDamagePlayer(EntityLivingBase instance, DamageSource source, float b0){
        if(source.getTrueSource() instanceof EntityAmalgalich &&
                !ForgeConfigHandler.server.amalgalichConfig.consumptionDamagesPlayers &&
                instance instanceof EntityPlayer){
            return false;
        }
        return true;
    }

    @ModifyArg(
            method = "updateTask",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"),
            index = 1,
            remap = true
    )
    public float lycanitesTweaks_lycanitesMobsEffectAuraGoal_updateTaskDamageMaxHP(float amount, @Local EntityLivingBase target, @Local DamageSource damageSource){
        if(damageSource.getTrueSource() instanceof EntityAmalgalich &&
                ForgeConfigHandler.server.amalgalichConfig.consumptionDamageMaxHP){
            return target.getMaxHealth();
        }
        return amount;
    }
}
