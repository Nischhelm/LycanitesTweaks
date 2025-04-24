package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevels;

import com.lycanitesmobs.core.pets.PetEntry;
import com.lycanitesmobs.core.pets.PetManager;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PetManager.class)
public abstract class PetManagerTrackHighestLevelMixin {

    @Shadow(remap = false)
    public EntityLivingBase host;

    @Inject(
            method = "addEntry",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
            remap = false
    )
    public void www(PetEntry petEntry, CallbackInfo ci){
        IPlayerMobLevelCapability pml = this.host.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
        if(pml != null) pml.addPetEntryLevels(petEntry);
    }

    @Inject(
            method = "removeEntry",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"),
            remap = false
    )
    public void aaa(PetEntry petEntry, CallbackInfo ci){
        IPlayerMobLevelCapability pml = this.host.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
        if(pml != null) pml.removePetEntryLevels(petEntry);
    }
}
