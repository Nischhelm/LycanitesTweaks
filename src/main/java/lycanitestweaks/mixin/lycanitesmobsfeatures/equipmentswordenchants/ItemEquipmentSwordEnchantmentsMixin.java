package lycanitestweaks.mixin.lycanitesmobsfeatures.equipmentswordenchants;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nonnull;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipmentSwordEnchantmentsMixin extends ItemBase {

    @Shadow(remap = false)
    public abstract NonNullList<ItemStack> getEquipmentPartStacks(ItemStack itemStack);
    @Shadow(remap = false)
    public abstract ItemEquipmentPart getEquipmentPart(ItemStack itemStack);

    @Override
    @Unique
    public boolean isEnchantable(@Nonnull ItemStack stack)
    {
        return true;
    }

    public int getItemEnchantability()
    {
        return 1;
    }

//    @Override
//    @Unique
//    public boolean isBookEnchantable(@Nonnull ItemStack stack, @Nonnull ItemStack book)
//    {
//        return lycanitesTweaks$getLowestLevel(stack) >= ForgeConfigHandler.server.equipmentMinLevelEnchantable;
//    }

    @Override
    @Unique
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment){
        if(lycanitesTweaks$getLowestLevel(stack) < ForgeConfigHandler.server.equipmentMinLevelEnchantable) return false;
        if(enchantment != Enchantments.SWEEPING &&
                ((enchantment.type == EnumEnchantmentType.WEAPON) ||
                (enchantment == Enchantments.EFFICIENCY))){
            return true;
        }
        else{
            return super.canApplyAtEnchantingTable(stack, enchantment);
        }
    }

    @Unique
    public int lycanitesTweaks$getLowestLevel(ItemStack equipmentStack) {
        int lowestLevel = ForgeConfigHandler.server.equipmentMinLevelEnchantable;

        for(ItemStack equipmentPartStack : this.getEquipmentPartStacks(equipmentStack)) {
            ItemEquipmentPart equipmentPart = this.getEquipmentPart(equipmentPartStack);
            if (equipmentPart != null) {
                int partLevel = equipmentPart.getLevel(equipmentPartStack);
                if (partLevel < lowestLevel) {
                    lowestLevel = partLevel;
                }
            }
        }

        return lowestLevel;
    }
}
