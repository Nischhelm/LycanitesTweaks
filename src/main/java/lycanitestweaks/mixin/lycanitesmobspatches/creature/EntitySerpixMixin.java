package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntitySerpix;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntitySerpix.class)
public abstract class EntitySerpixMixin extends TameableCreatureEntity {

    public EntitySerpixMixin(World world) {
        super(world);
    }

    @ModifyArg(
            method = "attackRanged",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/RapidFireProjectileEntity;setPosition(DDD)V"),
            index = 1
    )
    private double lycanitesTweaks_lycanitesEntitySerpix_attackRanged(double y, @Local BlockPos launchPos){
        return (double)launchPos.getY() + (this.height / 2F);
    }
}
