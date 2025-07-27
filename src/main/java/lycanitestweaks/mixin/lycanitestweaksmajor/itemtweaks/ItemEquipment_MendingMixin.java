package lycanitestweaks.mixin.lycanitestweaksmajor.itemtweaks;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipment_MendingMixin extends ItemBase {

    @Shadow(remap = false)
    public abstract boolean addSharpness(ItemStack equipmentStack, int sharpness);
    @Shadow(remap = false)
    public abstract int getSharpness(ItemStack equipmentStack);

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void lycanitesTweaks_lycanitesMobsItemEquipment_init(CallbackInfo ci){
        this.setMaxDamage(ItemEquipment.SHARPNESS_MAX);
    }

    @Unique
    @Override
    public boolean isDamaged(ItemStack stack) {
        return this.getSharpness(stack) < ItemEquipment.SHARPNESS_MAX;
    }

    @Unique
    @Override
    public int getDamage(ItemStack stack) {
        return ItemEquipment.SHARPNESS_MAX - this.getSharpness(stack);
    }

    @Unique
    @Override
    public void setDamage(ItemStack stack, int damage){
        int repair = this.getDamage(stack) - damage;
        if(repair > 0) this.addSharpness(stack, repair);
    }
}
