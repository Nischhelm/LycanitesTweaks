package lycanitestweaks.mixin.lycanitestweaksmajor.playermoblevels;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.pets.PetEntry;
import lycanitestweaks.capability.playermoblevel.IPlayerMobLevelCapability;
import lycanitestweaks.capability.playermoblevel.PlayerMobLevelCapability;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PetEntry.class)
public abstract class PetEntryTrackHighestLevelMixin {

    @Shadow(remap = false)
    public EntityLivingBase host;

    // New pet entry does not contain level information as it is stored by the mob itself
    @Inject(
            method = "createFromEntity",
            at = @At("RETURN"),
            remap = false
    )
    private static void lycanitesTweaks_lycanitesMobsPetEntry_createFromEntityPML(EntityPlayer player, BaseCreatureEntity entity, String petType, CallbackInfoReturnable<PetEntry> cir){
        IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(player);
        if(pml != null) pml.addNewPetLevels(entity.getLevel());
    }

    @Inject(
            method = "setSpawningActive",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/info/CreatureManager;getInstance()Lcom/lycanitesmobs/core/info/CreatureManager;"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsPetEntry_setSpawningActiveCacheDirty(boolean spawningActive, CallbackInfoReturnable<PetEntry> cir){
        if(this.host instanceof EntityPlayer) {
            IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer((EntityPlayer) this.host);
            if (pml != null) pml.clearHighestLevelPetActive();
        }
    }
}
