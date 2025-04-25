package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevels;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.pets.PetEntry;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PetEntry.class)
public abstract class PetEntryTrackHighestLevelMixin {

    // New pet entry does not contain level information as it is stored by the mob itself
    @Inject(
            method = "createFromEntity",
            at = @At("RETURN"),
            remap = false
    )
    private static void lycanitesTweaks_lycanitesMobsPetEntry_createFromEntityPML(EntityPlayer player, BaseCreatureEntity entity, String petType, CallbackInfoReturnable<PetEntry> cir){
        IPlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
        if(pml != null) pml.addNewPetLevels(entity.getLevel());
    }
}
