package lycanitestweaks.mixin.lycanitesmobsfeatures.equipmentswordenchants;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipmentSwordEnchantmentsMixin extends ItemBase {

    @Override
    @Unique
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment){
        if(enchantment != Enchantments.SWEEPING && (enchantment.type == EnumEnchantmentType.WEAPON)){
            return true;
        }
        else{
            return super.canApplyAtEnchantingTable(stack, enchantment);
        }
    }
}
