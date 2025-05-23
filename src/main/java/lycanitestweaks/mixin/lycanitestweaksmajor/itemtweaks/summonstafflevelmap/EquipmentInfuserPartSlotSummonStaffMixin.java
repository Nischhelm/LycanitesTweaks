package lycanitestweaks.mixin.lycanitestweaksmajor.itemtweaks.summonstafflevelmap;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.container.EquipmentInfuserPartSlot;
import lycanitestweaks.util.IItemStaffSummoningElementLevelMapMixin;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentInfuserPartSlot.class)
public abstract class EquipmentInfuserPartSlotSummonStaffMixin {

    @ModifyReturnValue(
            method = "isItemValid",
            at = @At("RETURN")
    )
    public boolean lycanitesTweaks_lycanitesMobsEquipmentInfuserPartSlot_isItemValidLevelMapItem(boolean original, @Local(argsOnly = true) ItemStack itemStack){
        return original || itemStack.getItem() instanceof IItemStaffSummoningElementLevelMapMixin;
    }
}
