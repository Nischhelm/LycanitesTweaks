package lycanitestweaks.mixin.lycanitestweaksmajor.summoningrework;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.entity.PortalEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import com.lycanitesmobs.core.tileentity.TileEntitySummoningPedestal;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = PortalEntity.class, priority = 1001)
public abstract class PortalEntityReworkMixin {

    @Unique
    private static final UUID lycanitesTweaks$IP_DMG_UUID = UUID.fromString("e45e8101-a2df-4e82-b19c-b4c73fe3596c");
    @Unique
    private static final UUID lycanitesTweaks$IP_HP_UUID = UUID.fromString("d6fcfe2e-40ef-47ab-9f55-798351be490b");
    @Unique
    private static final UUID lycanitesTweaks$IP_RS_UUID = UUID.fromString("4a99583b-34a6-446c-ab37-040b7c5536c4");
    @Unique
    private static final String lycanitesTweaks$IP_DMG_ID = LycanitesTweaks.MODID + ":imperfectDamage";
    @Unique
    private static final String lycanitesTweaks$IP_HP_ID = LycanitesTweaks.MODID + ":imperfectHealth";
    @Unique
    private static final String lycanitesTweaks$IP_RS_ID = LycanitesTweaks.MODID + ":imperfectRangedSpeed";

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
    public boolean lycanitesTweaks_lycanitesMobsPortalEntity_summonCreaturesHostile(TameableCreatureEntity instance, EntityPlayer player) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(player);
        if (extendedPlayer == null) return true;

        if (ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectMinionNerfs &&
                !extendedPlayer.getBeastiary().hasKnowledgeRank(instance.creatureInfo.getName(), ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.variantSummonRank)) {
            lycanitesTweaks$isHostileToPlayer = player.getEntityWorld().rand.nextDouble() < Helpers.getImperfectHostileChance(extendedPlayer, instance.creatureInfo);
            if(lycanitesTweaks$isHostileToPlayer) {
                instance.setRevengeTarget(player);
                player.sendStatusMessage(new TextComponentTranslation("message.summon.imperfect.hostile"), true);
            }
            return !lycanitesTweaks$isHostileToPlayer;
        }
        return true;
    }

    // Need to apply this as late as possible, any stat refreshes would erase the stat mods set here
    @Inject(
            method = "summonCreatures",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/PortalEntity;shootingEntity:Lnet/minecraft/entity/player/EntityPlayer;", ordinal = 3),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsPortalEntity_summonCreaturesImperfect(CallbackInfoReturnable<Integer> cir, @Local BaseCreatureEntity entityCreature) {
        EntityPlayer player = this.shootingEntity;
        if(player == null && this.summoningPedestal.getOwnerUUID() != null)
            player = entityCreature.getEntityWorld().getPlayerEntityByUUID(this.summoningPedestal.getOwnerUUID());
        ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(player);

        if(extendedPlayer != null
                && ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectMinionNerfs
                && !lycanitesTweaks$isHostileToPlayer
                && !extendedPlayer.getBeastiary().hasKnowledgeRank(entityCreature.creatureInfo.getName(), ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.variantSummonRank)
        ){
            double lowerStatsChance = Helpers.getImperfectStatsChance(extendedPlayer, entityCreature.creatureInfo);
            if(player.getEntityWorld().rand.nextDouble() < lowerStatsChance){
                lowerStatsChance = Math.min(0.95D, lowerStatsChance);
                if(player.getEntityWorld().rand.nextBoolean()){
                    entityCreature.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier(lycanitesTweaks$IP_HP_UUID, lycanitesTweaks$IP_HP_ID, -lowerStatsChance, 2));
                    entityCreature.setHealth(entityCreature.getMaxHealth());
                    if(this.summoningPedestal == null) player.sendStatusMessage(new TextComponentTranslation("message.summon.imperfect.health"), true);
                }
                else{
                    entityCreature.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(lycanitesTweaks$IP_DMG_UUID, lycanitesTweaks$IP_DMG_ID, -lowerStatsChance, 2));
                    entityCreature.getEntityAttribute(BaseCreatureEntity.RANGED_SPEED).applyModifier(new AttributeModifier(lycanitesTweaks$IP_RS_UUID, lycanitesTweaks$IP_RS_ID, -lowerStatsChance, 2));
                    if(this.summoningPedestal == null) player.sendStatusMessage(new TextComponentTranslation("message.summon.imperfect.attack"), true);
                }
            }
        }
    }
}
