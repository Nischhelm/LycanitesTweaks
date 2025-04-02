package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevels;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.inventory.InventoryCreature;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InventoryCreature.class)
public abstract class InventoryCreatureBoundDimLimitedMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity creature;

    // Only stops putting items in, does not stop taking items out
    @ModifyReturnValue(
            method = "isItemValidForSlot",
            at = @At("RETURN"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsBaseCreatureEntity_isItemValidForSlot(boolean original){
        if(ForgeConfigHandler.server.pmlConfig.pmlMinionLimitDimNoInventory && ForgeConfigHandler.isDimensionLimitedMinion(this.creature.dimension)) return false;
        return original;
    }
}
