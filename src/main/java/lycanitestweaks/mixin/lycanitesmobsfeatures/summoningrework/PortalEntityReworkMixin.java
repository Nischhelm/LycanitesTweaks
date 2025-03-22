package lycanitestweaks.mixin.lycanitesmobsfeatures.summoningrework;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.entity.PortalEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalEntity.class)
public abstract class PortalEntityReworkMixin {

    @Unique
    private boolean lycanitesTweaks$isHostileToPlayer = false;

    @Shadow(remap = false)
    public EntityPlayer shootingEntity;

    @WrapWithCondition(
            method = "summonCreatures",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;setPlayerOwner(Lnet/minecraft/entity/player/EntityPlayer;)V"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesPortalEntity_summonHostile(TameableCreatureEntity instance, EntityPlayer player) {
        if (ForgeConfigHandler.server.imperfectSummoning && instance.getVariantIndex() == 0) {
            double hostileChance = ForgeConfigHandler.server.imperfectHostileBaseChance;
//            if (ForgeConfigHandler.server.imperfectHostileChanceModifier != 0.0D) {
//                ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(player);
//                if (extendedPlayer != null && extendedPlayer.getBeastiary().hasKnowledgeRank(instance.getName(), 1)) {
//                    hostileChance -= extendedPlayer.getBeastiary().getCreatureKnowledge(instance.getName()).experience * ForgeConfigHandler.server.imperfectHostileChanceModifier;
//                }
//            }
            lycanitesTweaks$isHostileToPlayer = player.getEntityWorld().rand.nextDouble() < hostileChance;
            if(lycanitesTweaks$isHostileToPlayer) instance.setRevengeTarget(player);
            return !lycanitesTweaks$isHostileToPlayer;
        }
        return true;
    }

    @Inject(
            method = "summonCreatures",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/item/temp/ItemStaffSummoning;applyMinionEffects(Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;)V"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesPortalEntity_summonCreatures(CallbackInfoReturnable<Integer> cir, @Local BaseCreatureEntity entityCreature) {
        if(ForgeConfigHandler.server.imperfectSummoning && !lycanitesTweaks$isHostileToPlayer && entityCreature.getVariantIndex() == 0){
            double lowerStatsChance = ForgeConfigHandler.server.imperfectStatsBaseChance;
//            if(ForgeConfigHandler.server.imperfectStatsChanceModifier != 0.0D){
//                ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(this.shootingEntity);
//                if(extendedPlayer != null && extendedPlayer.getBeastiary().hasKnowledgeRank(entityCreature.getName(), 0)){
//                    LycanitesTweaks.LOGGER.log(Level.INFO, "Attempt lower stat loss chance with Experience: " + extendedPlayer.getBeastiary().getCreatureKnowledge(entityCreature.getName()).experience);
//                    lowerStatsChance -= extendedPlayer.getBeastiary().getCreatureKnowledge(entityCreature.getName()).experience * ForgeConfigHandler.server.imperfectStatsChanceModifier;
//                }
//            }
            if(this.shootingEntity.getEntityWorld().rand.nextDouble() < lowerStatsChance){
                lowerStatsChance = Math.max(0.1D, 1.0F - lowerStatsChance);
                if(this.shootingEntity.getEntityWorld().rand.nextBoolean()){
                    LycanitesTweaks.LOGGER.log(Level.INFO, "Attempt lower HP");
                    entityCreature.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(entityCreature.getMaxHealth() * lowerStatsChance);
                    entityCreature.setHealth(entityCreature.getMaxHealth());
                }
                else{
                    LycanitesTweaks.LOGGER.log(Level.INFO, "Attempt lower damage");
                    entityCreature.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(entityCreature.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * lowerStatsChance);
                }
            }
        }
    }
}
