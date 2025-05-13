package lycanitestweaks.mixin.lycanitesmobsfeatures.equipmentswordenchants;

import com.lycanitesmobs.core.item.ItemBase;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import lycanitestweaks.handlers.ForgeConfigHandler;
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
            at = @At("TAIL"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsItemEquipment_addInformation(ItemStack itemStack, World world, List<String> tooltip, ITooltipFlag tooltipFlag, CallbackInfo ci){
        if(lycanitesTweaks$getLowestLevel(itemStack)>= ForgeConfigHandler.server.equipmentMinLevelEnchantable){
            tooltip.add(I18n.format("item.equipment.description.mixin"));
        }
    }

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
