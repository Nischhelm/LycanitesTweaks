package lycanitestweaks.mixin.lycanitesmobspatches.core.containers;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.core.container.BaseContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseContainer.class)
public abstract class BaseContainerMixin {

    @ModifyExpressionValue(
            method = "transferStackInSlot",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/container/BaseContainer;playerInventoryFinish:I", ordinal = 1, remap = false)
    )
    public int lycanitesTweaks_lycanitesMobsBaseContainer_transferStackInSlot(int original) {
        //Moving back to player inventory ignored last slot (bottom right in inventory, not hotbar) bc of an off-by-one
        return original + 1;
    }
}
