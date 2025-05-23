package lycanitestweaks.mixin.lycanitestweaksmajor.itemtweaks.summonstafflevelmap;

import com.lycanitesmobs.core.container.EquipmentInfuserChargeSlot;
import com.lycanitesmobs.core.container.EquipmentInfuserContainer;
import com.lycanitesmobs.core.container.EquipmentInfuserPartSlot;
import com.lycanitesmobs.core.info.ElementInfo;
import com.lycanitesmobs.core.item.ChargeItem;
import lycanitestweaks.util.IItemStaffSummoningElementLevelMapMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EquipmentInfuserContainer.class)
public abstract class EquipmentInfuserContainerSummonStaffMixin {

    @Shadow(remap = false)
    EquipmentInfuserChargeSlot chargeSlot;
    @Shadow(remap = false)
    EquipmentInfuserPartSlot partSlot;

    @Shadow(remap = false)
    public abstract void attemptInfusion();

    @Inject(
            method = "attemptInfusion",
            at = @At("HEAD"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEquipmentInfuserContainer_attemptInfusionLevelMapItem(CallbackInfo ci){
        if (!this.partSlot.getStack().isEmpty() && this.partSlot.getStack().getItem() instanceof IItemStaffSummoningElementLevelMapMixin) {
            IItemStaffSummoningElementLevelMapMixin equipmentPart = (IItemStaffSummoningElementLevelMapMixin)this.partSlot.getStack().getItem();

            if (equipmentPart.lycanitesTweaks$isLevelingChargeItem(this.chargeSlot.getStack())) {
                boolean infused = false;
                equipmentPart.lycanitesTweaks$addElements(this.partSlot.getStack(), (ChargeItem)this.chargeSlot.getStack().getItem());

                for(ElementInfo elementInfo : ((ChargeItem)this.chargeSlot.getStack().getItem()).getElements()){
                    if(equipmentPart.lycanitesTweaks$hasElement(this.partSlot.getStack(), elementInfo)) {
                        int experienceGained = equipmentPart.lycanitesTweaks$getExperienceFromChargeItem(this.chargeSlot.getStack());
                        equipmentPart.lycanitesTweaks$addExperience(this.partSlot.getStack(), elementInfo.name, experienceGained);
                        infused = true;
                    }
                }
                if(infused) {
                    this.chargeSlot.decrStackSize(1);
                    this.attemptInfusion();
                }
            }
        }
    }
}
