package lycanitestweaks.mixin.lycanitesmobsfeatures.cleansedcustomlist;

import com.lycanitesmobs.core.item.consumable.ItemCleansingCrystal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;

@Mixin(ItemCleansingCrystal.class)
public abstract class ItemCleansingCrystalClickMixin {

    @Inject(
            method = "onItemRightClick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesItemCleansingCrystal_onItemRightClick(World world, EntityPlayer player, EnumHand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir){
        HashSet<Potion> potionsToRemove = new HashSet<>();
        for(PotionEffect effect : player.getActivePotionEffects()){
            if(ForgeConfigHandler.getCleansedCureEffects().contains(effect.getPotion().getRegistryName()))
                potionsToRemove.add(effect.getPotion());
        }
        for(Potion potion : potionsToRemove){
            player.removePotionEffect(potion);
        }
    }
}
