package lycanitestweaks.mixin.lycanitesmobsfeatures.moretamingoptions;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.RideableCreatureEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RideableCreatureEntity.class)
public abstract class RideableCreatureEntitySaddleLevelMixin extends AgeableCreatureEntity {

    public RideableCreatureEntitySaddleLevelMixin(World world) {
        super(world);
    }

    @ModifyReturnValue(
            method = "hasSaddle",
            at = @At("RETURN"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsRideableCreatureEntity_hasSaddleVanillaLimited(boolean original, @Local ItemStack saddleStack){
        if(!original){
            if(!saddleStack.isEmpty() && saddleStack.getItem() == Items.SADDLE) return true;
        }
        return original;
    }

    // Doing handling here is ridiculous
    // Fired twice for some reason, hide it with status message
    @ModifyExpressionValue(
            method = "getInteractCommands",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z"),
            remap = true
    )
    public boolean lycanitesTweaks_lycanitesMobsRideableCreatureEntity_getInteractCommandsVanillaLimited(boolean original, @Local(argsOnly = true) EntityPlayer player){
        if(!original) {
            if (!this.inventory.getEquipmentStack("saddle").isEmpty() &&
                    this.inventory.getEquipmentStack("saddle").getItem() == Items.SADDLE) {
                if (Helpers.isPracticallyFlying(this) && ForgeConfigHandler.server.vanillaSaddleNoFlying) {
                    String message = LanguageManager.translate("mount.fail.noflying");
                    message = message.replace("%levels%", "" + ForgeConfigHandler.server.vanillaSaddleLevelRequirement);
                    player.sendStatusMessage(new TextComponentString(message), true);
                    return true;
                } else if (this.getLevel() >= ForgeConfigHandler.server.vanillaSaddleLevelRequirement) {
                    return false;
                } else if (player instanceof EntityPlayer) {
                    String message = LanguageManager.translate("mount.fail.saddlelevel");
                    message = message.replace("%levels%", "" + ForgeConfigHandler.server.vanillaSaddleLevelRequirement);
                    player.sendStatusMessage(new TextComponentString(message), true);
                    return true;
                }
            }
        }
        return original;
    }
}
