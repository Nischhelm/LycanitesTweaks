package lycanitestweaks.mixin.rlcombat;

import bettercombat.mod.util.Helpers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.features.EffectEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.EquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.ProjectileEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.SummonEquipmentFeature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Helpers.class)
public abstract class HelpersLycanitesEquipmentMixin {

    @WrapOperation(
            method = "attackTargetEntityItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getDistanceSq(Lnet/minecraft/entity/Entity;)D"),
            remap = true
    )
    private static double lycanitesTweaks_rlCombatHelpers_doArcSweep(EntityPlayer instance, Entity living, Operation<Double> original, @Local ItemStack weapon, @Local(ordinal = 3) double reach){
        if(weapon.getItem() instanceof ItemEquipment){
            if(instance.getDistanceSq(living) > reach * reach) return 2D * reach * reach;

            ItemEquipment lycanitesEquipment = (ItemEquipment)weapon.getItem();
            double sweepAngle = lycanitesEquipment.getDamageSweep(weapon) / (double)2.0F;

            double targetXDist = living.posX - instance.posX;
            double targetZDist = instance.posZ - living.posZ;
            double targetAngleAbsolute = (double)180.0F + Math.toDegrees(Math.atan2(targetXDist, targetZDist));
            double targetAngle = Math.abs(targetAngleAbsolute - (double)instance.rotationYaw);
            if (targetAngle > (double)180.0F) {
                targetAngle = (double)180.0F - (targetAngle - (double)180.0F);
            }

            if (targetAngle > sweepAngle){
                return 2D * reach * reach;
            }


            boolean usedMana = false;

            if(living instanceof EntityLivingBase) {
                EntityLivingBase target = (EntityLivingBase)living;
                for (EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(weapon, "effect")) {
                    EffectEquipmentFeature effectFeature = (EffectEquipmentFeature) equipmentFeature;
                    effectFeature.onHitEntity(weapon, target, instance);
                }

                for (EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(weapon, "summon")) {
                    SummonEquipmentFeature summonFeature = (SummonEquipmentFeature) equipmentFeature;
                    usedMana = summonFeature.onHitEntity(weapon, target, instance) || usedMana;
                }

                for (EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(weapon, "projectile")) {
                    ProjectileEquipmentFeature projectileFeature = (ProjectileEquipmentFeature) equipmentFeature;
                    usedMana = projectileFeature.onHitEntity(weapon, target, instance) || usedMana;
                }
            }
            if (usedMana) {
                lycanitesEquipment.removeMana(weapon, 1);
            }
            lycanitesEquipment.removeSharpness(weapon, 1);
            return reach;
        }
        return original.call(instance, living);
    }
}
