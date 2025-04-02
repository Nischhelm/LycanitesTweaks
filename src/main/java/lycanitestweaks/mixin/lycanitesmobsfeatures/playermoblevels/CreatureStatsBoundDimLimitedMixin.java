package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevels;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.CreatureStats;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreatureStats.class)
public abstract class CreatureStatsBoundDimLimitedMixin {

    @Shadow(remap = false)
    public BaseCreatureEntity entity;

    // Use limited stat calc for Soulbounds, doesn't reflect nametag levels but won't delete actual levels.
    @ModifyExpressionValue(
            method = "getLevelMultiplier",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;getLevel()I"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesMobsPetEntry_onUpdate(int original){
        if(ForgeConfigHandler.isDimensionLimitedMinion(this.entity.dimension) && this.entity.isBoundPet()){
            IPlayerMobLevelCapability pml = this.entity.getPetEntry().host.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
            if(pml != null){
                int levels = Math.min(original, pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.pmlConfig.pmlSoulboundDegree));
                String message = LanguageManager.translate("soulbound.limited.levels");
                message = message.replace("%levels%", ""+levels);
                ((EntityPlayer)this.entity.getPetEntry().host).sendStatusMessage(new TextComponentString(message), true);
                return levels;
            }
        }
        return original;
    }
}
