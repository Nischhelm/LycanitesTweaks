package lycanitestweaks.mixin.switchbow;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import de.Whitedraco.switchbow.entity.arrow.upgrade.EntityArrowSplitLoveUpgrade;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(EntityArrowSplitLoveUpgrade.class)
public abstract class EntityArrowSplitLoveUpgradeMixin {


    @WrapWithCondition(
            method = "AOEEffekt",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2),
            remap = false
    )
    public <E> boolean lycanitesTweaks_switchbowEntityArrowSplitLoveUpgrade_AOEEffekt(List<Entity> instance, E entity, @Local(ordinal = 0) LocalIntRef Count){
        if(entity instanceof AgeableCreatureEntity && lycanitesTweaks$canCreatureBreed((AgeableCreatureEntity)entity)){
            ((AgeableCreatureEntity) entity).breed();
            Count.set(Count.get() - 1);
            return false;
        }
        return true;
    }

    @Unique
    private static boolean lycanitesTweaks$canCreatureBreed(AgeableCreatureEntity creature){
        return creature.canBreed() && (!ForgeConfigHandler.server.loveArrowBreedAnimalsOnly || CreatureManager.getInstance().creatureGroups.get("animal").hasEntity(creature));
    }
}
