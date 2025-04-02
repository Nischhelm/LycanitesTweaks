package lycanitestweaks.util;

import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.features.EffectEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.EquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.ProjectileEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.SummonEquipmentFeature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;


public class Helpers {

    public static void  removeAllPositiveEffects(EntityLivingBase entity){
        ArrayList<Potion> toRemove = new ArrayList<>();

        for(PotionEffect effect : entity.getActivePotionEffects())
            if(!effect.getPotion().isBadEffect()) toRemove.add(effect.getPotion());

        for(Potion potion : toRemove) entity.removePotionEffect(potion);
    }

    // Performs hit effect without dealing damage
    public static void doEquipmentHitEffect(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker){
        if(!(itemStack.getItem() instanceof ItemEquipment)) return;

        ItemEquipment lycanitesEquipment = (ItemEquipment)itemStack.getItem();

        boolean usedMana = false;

        for(EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(itemStack, "effect")) {
            EffectEquipmentFeature effectFeature = (EffectEquipmentFeature)equipmentFeature;
            effectFeature.onHitEntity(itemStack, target, attacker);
        }

        for(EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(itemStack, "summon")) {
            SummonEquipmentFeature summonFeature = (SummonEquipmentFeature)equipmentFeature;
            usedMana = summonFeature.onHitEntity(itemStack, target, attacker) || usedMana;
        }

        for(EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(itemStack, "projectile")) {
            ProjectileEquipmentFeature projectileFeature = (ProjectileEquipmentFeature)equipmentFeature;
            usedMana = projectileFeature.onHitEntity(itemStack, target, attacker) || usedMana;
        }

        if (usedMana) {
          lycanitesEquipment.removeMana(itemStack, 1);
        }
    }
}