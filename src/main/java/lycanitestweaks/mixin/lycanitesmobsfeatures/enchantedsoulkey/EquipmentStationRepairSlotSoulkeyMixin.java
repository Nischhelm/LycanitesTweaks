package lycanitestweaks.mixin.lycanitesmobsfeatures.enchantedsoulkey;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.container.EquipmentStationRepairSlot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentStationRepairSlot.class)
public abstract class EquipmentStationRepairSlotSoulkeyMixin {

    @ModifyReturnValue(
            method = "isItemValid",
            at = @At("RETURN"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsEquipmentStationRepairSlot_isItemValidSoulkey(boolean original, @Local(argsOnly = true) ItemStack itemStack){
        if(original || itemStack.getItem() == Items.NETHER_STAR) return true;

        ResourceLocation checkId = itemStack.getItem().getRegistryName();
        if(new ResourceLocation("minecraft:diamond_block").equals(checkId)
                || new ResourceLocation("minecraft:emerald_block").equals(checkId)){
            return true;
        }
        return original;
    }
}
