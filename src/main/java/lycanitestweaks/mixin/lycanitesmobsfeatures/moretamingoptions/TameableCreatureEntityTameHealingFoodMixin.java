package lycanitestweaks.mixin.lycanitesmobsfeatures.moretamingoptions;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TameableCreatureEntity.class)
public abstract class TameableCreatureEntityTameHealingFoodMixin extends AgeableCreatureEntity {

    @Shadow(remap = false)
    public abstract boolean isHealingItem(ItemStack itemStack);
    @Shadow(remap = false)
    public abstract void playEatSound();

    public TameableCreatureEntityTameHealingFoodMixin(World world) {
        super(world);
    }

    @ModifyExpressionValue(
            method = "getInteractCommands",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;isTamingItem(Lnet/minecraft/item/ItemStack;)Z"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsTameableCreatureEntity_getInteractCommandsTameWithHealingFood(boolean original, @Local(argsOnly = true) EntityPlayer player, @Local(argsOnly = true) ItemStack itemStack){
        if(!original) {
            if(this.isHealingItem(itemStack)) {
                if (Helpers.isPracticallyFlying(this) && !ForgeConfigHandler.server.tamedWithFoodAllowFlying) {
                    player.sendStatusMessage(new TextComponentString(LanguageManager.translate("tame.fail.healingnofly")), true);
                    return false;
                }
                return true;
            }
        }
        return original;
    }

    @WrapWithCondition(
            method = "performCommand",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;tame(Lnet/minecraft/entity/player/EntityPlayer;)Z"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsTameableCreatureEntity_performCommandTameWithHealingFood(TameableCreatureEntity instance, EntityPlayer player, @Local(argsOnly = true) ItemStack itemStack){
        if(this.isHealingItem(itemStack) && this.getEntityWorld().rand.nextFloat() > ForgeConfigHandler.server.tameWithFoodChance) {
            this.playEatSound();
            return false;
        }
        return true;
    }
}
