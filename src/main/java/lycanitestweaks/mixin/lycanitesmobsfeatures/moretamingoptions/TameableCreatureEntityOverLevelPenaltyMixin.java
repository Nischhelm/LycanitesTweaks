package lycanitestweaks.mixin.lycanitesmobsfeatures.moretamingoptions;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TameableCreatureEntity.class)
public abstract class TameableCreatureEntityOverLevelPenaltyMixin extends AgeableCreatureEntity {

    public TameableCreatureEntityOverLevelPenaltyMixin(World world) {
        super(world);
    }

    @ModifyArg(
            method = "tame",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/CreatureRelationshipEntry;increaseReputation(I)Z"),
            remap = true
    )
    public int www(int amount, @Local(argsOnly = true) EntityPlayer player){
        IPlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
        if(ForgeConfigHandler.server.pmlConfig.pmlTamedOverLevelStartLevel > 0){

            if(pml != null){
                if(this.getLevel() >
                        Math.max(0, ForgeConfigHandler.server.pmlConfig.pmlTamedOverLevelStartLevel - pml.getHighestLevelPet()))
                    return Math.max(1, amount * Math.max(0, ForgeConfigHandler.server.pmlConfig.pmlTamedOverLevelStartLevel - pml.getHighestLevelPet()) / this.getLevel());
            }
            else{
                if(this.getLevel() > ForgeConfigHandler.server.pmlConfig.pmlTamedOverLevelStartLevel)
                    return Math.max(1, amount * ForgeConfigHandler.server.pmlConfig.pmlTamedOverLevelStartLevel / this.getLevel());
            }
        }
        return amount;
    }
}
