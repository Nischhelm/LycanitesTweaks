package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevels;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.PortalEntity;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalEntity.class)
public abstract class PortalEntitySummonPMLMixin {

    @Shadow(remap = false)
    public EntityPlayer shootingEntity;

    // Set levels for summons bc don't have to care about saving creature stats
    @Inject(
            method = "summonCreatures",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/PortalEntity;shootingEntity:Lnet/minecraft/entity/player/EntityPlayer;", ordinal = 0),
            remap = false
    )
    public void lycanitesTweaks_lycanitesPortalEntity_summonCreatures(CallbackInfoReturnable<Integer> cir, @Local BaseCreatureEntity entityCreature) {
        if(ForgeConfigHandler.mixinConfig.playerMobLevelSummonStaff){
            IPlayerMobLevelCapability pml = this.shootingEntity.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
            if(pml != null){
                entityCreature.setLevel(pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.pmlConfig.pmlSummonDegree));
            }
        }
    }

    // Actually undoes reworked summon weakened stats
//    // Attempt to fix creature state check desyncs and client desyncs
//    @Inject(
//            method = "summonCreatures",
//            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/PortalEntity;shootingEntity:Lnet/minecraft/entity/player/EntityPlayer;", ordinal = 3),
//            remap = false
//    )
//    public void lycanitesTweaks_lycanitesPortalEntity_summonCreaturesRefreshAttributes(CallbackInfoReturnable<Integer> cir, @Local BaseCreatureEntity entityCreature){
//        entityCreature.refreshAttributes();
//    }
}
