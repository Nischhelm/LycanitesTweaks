package lycanitestweaks.mixin.lycanitestweakscore.enchantedsoulkey;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.container.EquipmentStationEquipmentSlot;
import lycanitestweaks.item.ItemEnchantedSoulkey;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentStationEquipmentSlot.class)
public abstract class EquipmentStationEquipmentSlotSoulkeyMixin {

    @ModifyReturnValue(
            method = "isItemValid",
            at = @At("RETURN")
    )
    public boolean lycanitesTweaks_lycanitesMobsEquipmentStationEquipmentSlot_isItemValidSoulkey(boolean original, @Local(argsOnly = true) ItemStack itemStack){
        return original || itemStack.getItem() instanceof ItemEnchantedSoulkey;
    }
}
