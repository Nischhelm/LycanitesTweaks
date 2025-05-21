package lycanitestweaks.mixin.lycanitestweaksmajor.summoningrework;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.entity.PortalEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import com.lycanitesmobs.core.tileentity.TileEntitySummoningPedestal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
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
    @Shadow(remap = false)
    public TileEntitySummoningPedestal summoningPedestal;

    @WrapWithCondition(
            method = "summonCreatures",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/TameableCreatureEntity;setPlayerOwner(Lnet/minecraft/entity/player/EntityPlayer;)V"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesPortalEntity_summonHostile(TameableCreatureEntity instance, EntityPlayer player) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(player);
        if (extendedPlayer == null) return true;

        if (ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectSummoning &&
                !extendedPlayer.getBeastiary().hasKnowledgeRank(instance.creatureInfo.getName(), ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.variantSummonRank)) {
            lycanitesTweaks$isHostileToPlayer = player.getEntityWorld().rand.nextDouble() < Helpers.getImperfectHostileChance(extendedPlayer, instance.creatureInfo);
            if(lycanitesTweaks$isHostileToPlayer) {
                instance.setRevengeTarget(player);
                player.sendStatusMessage(new TextComponentTranslation("summon.imperfect.hostile"), true);
            }
            return !lycanitesTweaks$isHostileToPlayer;
        }
        return true;
    }

    // Need to apply this as late as possible, any stat refreshes would erase the stat mods set here
    @Inject(
            method = "summonCreatures",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/PortalEntity;summonDuration:I", ordinal = 0),
            remap = false
    )
    public void lycanitesTweaks_lycanitesPortalEntity_summonImperfect(CallbackInfoReturnable<Integer> cir, @Local BaseCreatureEntity entityCreature) {
        EntityPlayer player = this.shootingEntity;
        if(player == null && this.summoningPedestal.getOwnerUUID() != null)
            player = entityCreature.getEntityWorld().getPlayerEntityByUUID(this.summoningPedestal.getOwnerUUID());
        ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(player);

        if(extendedPlayer != null
                && ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectSummoning
                && !lycanitesTweaks$isHostileToPlayer
                && !extendedPlayer.getBeastiary().hasKnowledgeRank(entityCreature.creatureInfo.getName(), ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.variantSummonRank)
        ){
            double lowerStatsChance = Helpers.getImperfectStatsChance(extendedPlayer, entityCreature.creatureInfo);
            if(player.getEntityWorld().rand.nextDouble() < lowerStatsChance){
                lowerStatsChance = Math.max(0.1D, 1.0F - lowerStatsChance);
                if(player.getEntityWorld().rand.nextBoolean()){
                    entityCreature.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(entityCreature.getMaxHealth() * lowerStatsChance);
                    if(this.summoningPedestal == null) player.sendStatusMessage(new TextComponentTranslation("summon.imperfect.health"), true);
                }
                else{
                    entityCreature.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(entityCreature.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * lowerStatsChance);
                    entityCreature.getEntityAttribute(BaseCreatureEntity.RANGED_SPEED).setBaseValue(entityCreature.getEntityAttribute(BaseCreatureEntity.RANGED_SPEED).getAttributeValue() * lowerStatsChance);
                    if(this.summoningPedestal == null) player.sendStatusMessage(new TextComponentTranslation("summon.imperfect.attack"), true);
                }
            }
        }
    }
}
