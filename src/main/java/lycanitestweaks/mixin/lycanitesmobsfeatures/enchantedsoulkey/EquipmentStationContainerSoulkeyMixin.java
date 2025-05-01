package lycanitestweaks.mixin.lycanitesmobsfeatures.enchantedsoulkey;

import com.lycanitesmobs.core.container.EquipmentStationContainer;
import com.lycanitesmobs.core.container.EquipmentStationEquipmentSlot;
import com.lycanitesmobs.core.container.EquipmentStationRepairSlot;
import lycanitestweaks.item.ItemEnchantedSoulkey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EquipmentStationContainer.class)
public abstract class EquipmentStationContainerSoulkeyMixin {

    @Shadow(remap = false)
    EquipmentStationEquipmentSlot equipmentSlot;
    @Shadow(remap = false)
    EquipmentStationRepairSlot repairSlot;
    @Shadow(remap = false)
    public abstract void attemptRepair();

    @Inject(
            method = "attemptRepair",
            at = @At("HEAD"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEquipmentStationContainer_attemptRepairSoulkey(CallbackInfo ci){
        if (!this.equipmentSlot.getStack().isEmpty() && this.equipmentSlot.getStack().getItem() instanceof ItemEnchantedSoulkey) {
            int sharpness = ItemEnchantedSoulkey.getSoulkeySharpnessRepair(this.repairSlot.getStack());
            int mana = ItemEnchantedSoulkey.getSoulkeyManaRepair(this.repairSlot.getStack());
            if (sharpness > 0 || mana > 0) {
                ItemEnchantedSoulkey equipment = (ItemEnchantedSoulkey)this.equipmentSlot.getStack().getItem();
                boolean repaired = false;
                if (sharpness > 0) {
                    repaired = equipment.addSharpness(this.equipmentSlot.getStack(), sharpness);
                }

                if (mana > 0) {
                    repaired = equipment.addMana(this.equipmentSlot.getStack(), mana) || repaired;
                }

                if (repaired) {
                    this.repairSlot.decrStackSize(1);
                    this.attemptRepair();
                }
            }
        }
    }
}
