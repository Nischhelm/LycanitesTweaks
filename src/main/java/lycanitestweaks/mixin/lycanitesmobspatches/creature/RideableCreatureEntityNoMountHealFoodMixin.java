package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.RideableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RideableCreatureEntity.class)
public abstract class RideableCreatureEntityNoMountHealFoodMixin extends TameableCreatureEntity {

    public RideableCreatureEntityNoMountHealFoodMixin(World world) {
        super(world);
    }

    @ModifyExpressionValue(
            method = "getInteractCommands",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/RideableCreatureEntity;canBeMounted(Lnet/minecraft/entity/Entity;)Z"),
            remap = false
    )
    public boolean lycanitestweaks_lycanitesMobsRideableCreatureEntity_getInteractCommands(boolean original, @Local(argsOnly = true) EntityPlayer player){
        if(this.isHealingItem(player.getHeldItemMainhand())) return false;
        return original;
    }
}
