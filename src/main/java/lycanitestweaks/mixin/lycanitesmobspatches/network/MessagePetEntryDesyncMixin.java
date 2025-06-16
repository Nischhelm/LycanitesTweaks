package lycanitestweaks.mixin.lycanitesmobspatches.network;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.network.MessagePetEntry;
import com.lycanitesmobs.core.pets.PetEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessagePetEntry.class)
public abstract class MessagePetEntryDesyncMixin {

    @Inject(
            method = "onMessage",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/pets/PetEntry;isRespawning:Z"),
            remap = false
    )
    private static void lycanitesTweaks_lycanitesMobsMessagePetEntry_onMessage(MessagePetEntry message, MessageContext ctx, EntityPlayer player, CallbackInfo ci, @Local PetEntry petEntry){
        if(petEntry.entity instanceof BaseCreatureEntity) ((BaseCreatureEntity) petEntry.entity).setPetEntry(petEntry);
    }
}
