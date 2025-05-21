package lycanitestweaks.mixin.lycanitestweaksmajor.rlcombatcompat;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipmentRLCombatSweepMixin extends ItemBase {

    @Redirect(
            method = "hitEntity",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/equipment/ItemEquipment;getDamageSweep(Lnet/minecraft/item/ItemStack;)D", remap = false)
    )
    private double lycanitesTweaks_lycanitesItemEquipment_hitEntity(ItemEquipment instance, ItemStack equipmentFeature){
        return 0.0D;
    }
}
