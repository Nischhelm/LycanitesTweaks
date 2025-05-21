package lycanitestweaks.mixin.lycanitestweaksmajor.interacttweaks;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.inventory.InventoryCreature;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryCreature.class)
public abstract class InventoryCreatureSaddleLevelMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity creature;

    // Config for no flying with vanilla saddle handled in RideableCreature
    @Inject(
            method = "getSlotForEquipment",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 5),
            cancellable = true
    )
    public void lycanitesTweaks_lycanitesMobsInventoryCreature_getSlotForEquipmentVanillaSaddle(ItemStack itemStack, CallbackInfoReturnable<String> cir){
        if(itemStack.getItem() == Items.SADDLE) {
            cir.setReturnValue("saddle");
        }
    }
}
