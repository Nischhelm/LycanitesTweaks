package lycanitestweaks.mixin.ReachFix;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import meldexun.reachfix.util.ReachFixUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ReachFixUtil.class)
public abstract class ReachFixUtilMixin {

    @ModifyReturnValue(
            method = "getEntityReach",
            at = @At("RETURN"),
            remap = false
    )
    private static double lycanitesTweaks_reachFixReachFixUtil_getEntityReach(double original, @Local double reach, @Local(argsOnly = true) EntityPlayer player, @Local(argsOnly = true) EnumHand hand){
        return original + lycanitesTweaks$getLycanitesReachBonus(player, hand);
    }

    @Unique
    private static double lycanitesTweaks$getLycanitesReachBonus(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        Item item = stack.getItem();
        if(!(item instanceof ItemEquipment)){
            return (double)0.0F;
        }
        else{
            return ((ItemEquipment) item).getDamageRange(stack);
        }
    }
}
