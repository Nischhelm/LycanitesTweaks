package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.entity.creature.EntityAsmodeus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityAsmodeus.class)
public abstract class EntityAsmodeus_MinionNullMixin {

    // FermiumMixins
    @ModifyExpressionValue(
            method = "onLivingUpdate",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z", ordinal = 1)
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityAsmodeus_onLivingUpdateSeverCleanMinions(boolean isClient){
        return !isClient;
    }
}
