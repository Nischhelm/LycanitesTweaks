package lycanitestweaks.mixin.switchbow;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import de.Whitedraco.switchbow.entity.arrow.EntityArrowLove;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityArrowLove.class)
public abstract class EntityArrowLoveMixin {

    @WrapWithCondition(
            method = "arrowHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V", ordinal = 1)
    )
    public boolean lycanitesTweaks_switchbowEntityArrowSplitLoveUpgrade_AOEEffekt(EntityLivingBase instance, PotionEffect effect){
        if(instance instanceof AgeableCreatureEntity
                && CreatureManager.getInstance().creatureGroups.get("animal").hasEntity(instance)
                && ((AgeableCreatureEntity) instance).canBreed()){
            ((AgeableCreatureEntity) instance).breed();
            return false;
        }
        return true;
    }
}
