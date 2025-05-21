package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.FearEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FearEntity.class)
public abstract class FearEntityPlayerFlyingMixin extends BaseCreatureEntity {

    public FearEntityPlayerFlyingMixin(World world) {
        super(world);
    }

    @ModifyExpressionValue(
            method = "isFlying",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerCapabilities;isCreativeMode:Z")
    )
    public boolean lycanitesTweaks_lycanitesFearEntity_isFlyingSurvivalPlayer(boolean original){
        return ((EntityPlayer)this.pickupEntity).capabilities.allowFlying && ((EntityPlayer)this.pickupEntity).capabilities.isFlying;
    }
}
