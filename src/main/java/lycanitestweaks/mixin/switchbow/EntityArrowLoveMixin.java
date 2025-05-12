package lycanitestweaks.mixin.switchbow;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import de.Whitedraco.switchbow.entity.arrow.EntityArrowLove;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityArrowLove.class)
public abstract class EntityArrowLoveMixin {

    @WrapWithCondition(
            method = "arrowHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V", ordinal = 1),
            remap = true
    )
    public boolean lycanitesTweaks_switchbowEntityArrowSplitLoveUpgrade_AOEEffekt(EntityLivingBase instance, PotionEffect effect){
        if(instance instanceof AgeableCreatureEntity && lycanitesTweaks$canCreatureBreed((AgeableCreatureEntity)instance)){
            ((AgeableCreatureEntity) instance).breed();
            return false;
        }
        return true;
    }

    @Unique
    private static boolean lycanitesTweaks$canCreatureBreed(AgeableCreatureEntity creature){
        return creature.canBreed() && (!ForgeConfigHandler.server.loveArrowBreedAnimalsOnly || CreatureManager.getInstance().creatureGroups.get("animal").hasEntity(creature));
    }
}
