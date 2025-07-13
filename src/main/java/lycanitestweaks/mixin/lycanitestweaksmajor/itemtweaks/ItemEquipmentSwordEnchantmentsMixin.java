package lycanitestweaks.mixin.lycanitestweaksmajor.itemtweaks;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.ForgeConfigProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.List;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipmentSwordEnchantmentsMixin extends ItemBase {

    @Shadow(remap = false)
    public abstract NonNullList<ItemStack> getEquipmentPartStacks(ItemStack itemStack);
    @Shadow(remap = false)
    public abstract ItemEquipmentPart getEquipmentPart(ItemStack itemStack);

    @SideOnly(Side.CLIENT)
    @Inject(
            method = "addInformation",
            at = @At("TAIL")
    )
    public void lycanitesTweaks_lycanitesMobsItemEquipment_addInformation(ItemStack itemStack, World world, List<String> tooltip, ITooltipFlag tooltipFlag, CallbackInfo ci){
        if(lycanitesTweaks$getLowestPartLevel(itemStack) < ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipmentEnchantmentsMinLevelParts){
            if(ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipmentEnchantmentsMinLevelTooltips)
                tooltip.add(I18n.format("item.equipment.description.mixin.enchrequirement", ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipmentEnchantmentsMinLevelParts));
        }
        else {
            if(itemStack.isItemEnchanted()){
                if(ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipEnchDisassembleLock) tooltip.add(I18n.format("item.equipment.description.mixin.enchlock"));
                else tooltip.add(I18n.format("item.equipment.description.mixin.enchremove"));
            }
            else tooltip.add(I18n.format("item.equipment.description.mixin.enchantable"));
        }
    }

    @Override
    @Unique
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return true;
    }

    // Enchantment Table
    @Override
    @Unique
    public int getItemEnchantability() {
        return 1;
    }

    // Used by both Enchantment Table and Anvil
    /** Checks part level -> black list -> if Weapon enchantment OR check modded enchantment conditions. Super call is important for SME **/
    @Override
    @Unique
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment){
        if(lycanitesTweaks$getLowestPartLevel(stack) < ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipmentEnchantmentsMinLevelParts) return false;
        if(ForgeConfigProvider.getEquipmentEnchantmentBlacklist().contains(enchantment)) return false;

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
    public int lycanitesTweaks$getLowestPartLevel(ItemStack equipmentStack) {
        int lowestLevel = ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipmentEnchantmentsMinLevelParts;

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
