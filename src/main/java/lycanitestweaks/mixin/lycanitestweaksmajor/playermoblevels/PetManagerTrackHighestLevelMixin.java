package lycanitestweaks.mixin.lycanitestweaksmajor.playermoblevels;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.pets.PetEntry;
import com.lycanitesmobs.core.pets.PetManager;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PetManager.class)
public abstract class PetManagerTrackHighestLevelMixin {

    @Shadow(remap = false)
    public EntityLivingBase host;

    // hah I wish
//    @Inject(
//            method = "addEntry",
//            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
//            remap = false
//    )
//    public void www(PetEntry petEntry, CallbackInfo ci){
//        IPlayerMobLevelCapability pml = this.host.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
//        if(pml != null) pml.addPetEntryLevels(petEntry);
//    }

    @Inject(
            method = "removeEntry",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsPetManager_removeEntryPML(PetEntry petEntry, CallbackInfo ci){
        if(this.host instanceof EntityPlayer) {
            IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer((EntityPlayer) this.host);
            if (pml != null) pml.removePetEntryLevels(petEntry);
        }
    }

    // Accurately load saved levels
    @Inject(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/pets/PetManager;addEntry(Lcom/lycanitesmobs/core/pets/PetEntry;)V"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsPetManager_onUpdatePML(World world, CallbackInfo ci, @Local PetEntry petEntry){
        if(this.host instanceof EntityPlayer) {
            IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer((EntityPlayer) this.host);
            if (pml != null) pml.addPetEntryLevels(petEntry);
        }
    }
}
