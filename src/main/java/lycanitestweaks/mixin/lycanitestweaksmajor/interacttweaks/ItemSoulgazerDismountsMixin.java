package lycanitestweaks.mixin.lycanitestweaksmajor.interacttweaks;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.item.special.ItemSoulgazer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemSoulgazer.class)
public abstract class ItemSoulgazerDismountsMixin {

    @ModifyReturnValue(
            method = "onItemRightClickOnEntity",
            at = @At(value = "RETURN", ordinal = 2),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesItemSoulgazer_onItemRightClickOnEntity(boolean original, @Local(argsOnly = true) EntityPlayer player, @Local(argsOnly = true) Entity entity){
        if(entity.isRiding()) {
            entity.dismountRidingEntity();
            if(entity instanceof EntityLivingBase) ((EntityLivingBase) entity).setRevengeTarget(player);
        }
        return original;
    }
}
