package lycanitestweaks.util;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.features.EffectEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.EquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.ProjectileEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.SummonEquipmentFeature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Helpers {

    // mfw Lycanites config for no flying mount doesn't catch mobs whose flight check considers landed state
    public static boolean isPracticallyFlying(BaseCreatureEntity entity){
        return (entity.isFlying() || entity.flySoundSpeed > 0);
    }

    public static void cureActiveEffectsFromResourceSet(EntityLivingBase entity, HashSet<ResourceLocation> curingSet){
        List<Potion> potionsToRemove = new ArrayList<>();
        for(PotionEffect effect : entity.getActivePotionEffects()){
            if(curingSet.contains(effect.getPotion().getRegistryName()))
                potionsToRemove.add(effect.getPotion());
        }
        for(Potion potion : potionsToRemove){
            entity.removePotionEffect(potion);
        }
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

    public static void removeAllPositiveEffects(EntityLivingBase entity){
        ArrayList<Potion> toRemove = new ArrayList<>();

        for(PotionEffect effect : entity.getActivePotionEffects())
            if(!effect.getPotion().isBadEffect()) toRemove.add(effect.getPotion());

        for(Potion potion : toRemove) entity.removePotionEffect(potion);
    }
}