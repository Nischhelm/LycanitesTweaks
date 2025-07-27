package lycanitestweaks.mixin.lycanitestweaksmajor.itemtweaks;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.ForgeConfigProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

@Mixin(ItemEquipment.class)
public abstract class ItemEquipment_EnchantmentsMixin extends ItemBase {

    @Shadow(remap = false)
    public abstract NonNullList<ItemStack> getEquipmentPartStacks(ItemStack itemStack);
    @Shadow(remap = false)
    public abstract ItemEquipmentPart getEquipmentPart(ItemStack itemStack);
    @Shadow(remap = false)
    public abstract boolean removeSharpness(ItemStack equipmentStack, int sharpness);

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
                if(ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipEnchPreventsDisassemble) tooltip.add(I18n.format("item.equipment.description.mixin.enchlock"));
                else tooltip.add(I18n.format("item.equipment.description.mixin.enchremove"));
            }
            else tooltip.add(I18n.format("item.equipment.description.mixin.enchantable"));
        }
    }

    @Redirect(
            method = "onItemUse",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/equipment/ItemEquipment;removeSharpness(Lnet/minecraft/item/ItemStack;I)Z", remap = false)
    )
    public boolean lycanitesTweaks_lycanitesMobsItemEquipment_onItemUseUnbreaking(ItemEquipment instance, ItemStack equipmentPartStack, int i, @Local(argsOnly = true) EntityPlayer player, @Local ItemStack itemStack){
        return lycanitesTweaks$removeSharpnessWithUnbreaking(itemStack, i, player.getRNG());
    }

    @Redirect(
            method = "itemInteractionForEntity",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/equipment/ItemEquipment;removeSharpness(Lnet/minecraft/item/ItemStack;I)Z", remap = false)
    )
    public boolean lycanitesTweaks_lycanitesMobsItemEquipment_itemInteractionForEntityUnbreaking(ItemEquipment instance, ItemStack equipmentPartStack, int i, @Local(argsOnly = true) ItemStack itemStack, @Local(argsOnly = true) EntityPlayer player){
        return lycanitesTweaks$removeSharpnessWithUnbreaking(itemStack, i, player.getRNG());
    }

    @Redirect(
            method = "onBlockDestroyed",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/equipment/ItemEquipment;removeSharpness(Lnet/minecraft/item/ItemStack;I)Z", remap = false)
    )
    public boolean lycanitesTweaks_lycanitesMobsItemEquipment_onBlockDestroyedUnbreaking(ItemEquipment instance, ItemStack equipmentPartStack, int i, @Local(argsOnly = true) ItemStack itemStack, @Local(argsOnly = true) EntityLivingBase entityLiving){
        return lycanitesTweaks$removeSharpnessWithUnbreaking(itemStack, i, entityLiving.getRNG());
    }

    @Redirect(
            method = "hitEntity",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/equipment/ItemEquipment;removeSharpness(Lnet/minecraft/item/ItemStack;I)Z", remap = false)
    )
    public boolean lycanitesTweaks_lycanitesMobsItemEquipment_hitEntityUnbreaking(ItemEquipment instance, ItemStack equipmentPartStack, int i, @Local(argsOnly = true) ItemStack itemStack, @Local(argsOnly = true, ordinal = 1) EntityLivingBase attacker){
        return lycanitesTweaks$removeSharpnessWithUnbreaking(itemStack, i, attacker.getRNG());
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

        if((enchantment.type == EnumEnchantmentType.WEAPON || enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.UNBREAKING)){
            return true;
        }
        else if(enchantment.type == EnumEnchantmentType.DIGGER && ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.craftedEquipEnchDigger){
            return true;
        }
        else if(enchantment.type == EnumEnchantmentType.BREAKABLE && ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.mendingForEquipment){
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

    /**
     * Decreases the Sharpness of this Equipment by decreasing the Sharpness of all parts.
     * @param equipmentStack The itemStack of the Equipment.
     * @return True if Sharpness was decreased at all.
     */
    @Unique
    public boolean lycanitesTweaks$removeSharpnessWithUnbreaking(ItemStack equipmentStack, int sharpnessCost, Random rand) {
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, equipmentStack);
        int costReduction = 0;

        for (int costPoint = 0; enchantmentLevel > 0 && costPoint < sharpnessCost; ++costPoint) {
            if (EnchantmentDurability.negateDamage(equipmentStack, enchantmentLevel, rand)) {
                ++costReduction;
            }
        }

        sharpnessCost -= costReduction;
        if (sharpnessCost <= 0) sharpnessCost = 0;

        return this.removeSharpness(equipmentStack, sharpnessCost);
    }
}
