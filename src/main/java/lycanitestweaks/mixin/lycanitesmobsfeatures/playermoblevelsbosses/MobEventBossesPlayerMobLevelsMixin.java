package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevelsbosses;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.mobevent.MobEvent;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MobEvent.class)
public abstract class MobEventBossesPlayerMobLevelsMixin {

    @Unique
    private final String CHANNEL_BOSS = "boss";

    @Shadow(remap = false)
    public String channel;

    @ModifyArg(
            method = "onSpawn",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;addLevel(I)V"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobEvent_onSpawn(int level, @Local(argsOnly = true) EntityPlayer player){
        PlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
        if(pml != null){
            if(CHANNEL_BOSS.equals(this.channel)) return level + pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.bossPMLDegree);
        }
        return level;
    }
}
