package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevels;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.ExtendedWorld;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.spawner.MobSpawn;
import com.lycanitesmobs.core.spawner.Spawner;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Spawner.class)
public abstract class SpawnerPlayerMobLevelsMixin {

    @Shadow(remap = false)
    public String name;

    @Inject(
            method = "spawnEntity",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/spawner/Spawner;forceNoDespawn:Z"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesSpawner_spawnEntity(World world, ExtendedWorld worldExt, EntityLiving entityLiving, int level, MobSpawn mobSpawn, EntityPlayer player, int chain, CallbackInfo ci, @Local BaseCreatureEntity entityCreature){
        if(player != null) {
            PlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
            if (pml != null) {
                boolean isInList = ForgeConfigHandler.getPMLSpawnerNames().contains(this.name);

                isInList = ForgeConfigHandler.server.pmlSpawnerNameStringsIsBlacklist != isInList;

                if (isInList) entityCreature.applyLevel(entityCreature.getLevel() + pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.pmlSpawnerDegree));
            }
        }
    }
}
