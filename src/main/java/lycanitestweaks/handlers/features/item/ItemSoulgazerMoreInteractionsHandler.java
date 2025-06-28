package lycanitestweaks.handlers.features.item;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.item.special.ItemSoulgazer;
import lycanitestweaks.capability.*;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import lycanitestweaks.handlers.LycanitesTweaksRegistry;
import lycanitestweaks.handlers.config.major.PlayerMobLevelsConfig;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemSoulgazerMoreInteractionsHandler {

    // Copy from ItemSoulgazer
    private static boolean soulgazeAbility(EntityPlayer player, Entity entity) {
        ExtendedPlayer playerExt = ExtendedPlayer.getForPlayer(player);
        if(playerExt == null) {
            return false;
        }

        int amount = CreatureManager.getInstance().config.creatureStudyKnowledge;
        if (!playerExt.studyCreature(entity, amount, true, true)) {
            return false;
        }

        if(player.getEntityWorld().isRemote) {
            for(int i = 0; i < 32; ++i) {
                entity.getEntityWorld().spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                        entity.getPosition().getX() + (4.0F * player.getRNG().nextFloat()) - 2.0F,
                        entity.getPosition().getY() + (4.0F * player.getRNG().nextFloat()) - 2.0F,
                        entity.getPosition().getZ() + (4.0F * player.getRNG().nextFloat()) - 2.0F,
                        0.0D, 0.0D, 0.0D);
            }
        }

        return true;
    }

    @SubscribeEvent
    public static void soulgazeBlockBreak(BlockEvent.HarvestDropsEvent event){
        if(event.getHarvester() == null || event.getWorld().isRemote) return;
        EntityPlayer player = event.getHarvester();

        if(player.getHeldItemMainhand().getItem() instanceof ItemSoulgazer){
            if(event.getState().getBlock() == Blocks.CRAFTING_TABLE){
                event.getWorld().playSound(null, event.getPos(), LycanitesTweaksRegistry.SOULGAZER_CRAFTINGTABLE, SoundCategory.PLAYERS, 1F, 1F);
            }
        }
    }

    @SubscribeEvent
    public static void soulgazeAttackEntity(AttackEntityEvent event){
        if(event.isCanceled()) return;
        if(event.getEntityPlayer() == null) return;
        if(event.getTarget() == null || !(Helpers.hasSoulgazerEquiped(event.getEntityPlayer()))) return;

        ILycanitesTweaksPlayerCapability playerKeybinds = LycanitesTweaksPlayerCapability.getForPlayer(event.getEntityPlayer());
        if(playerKeybinds != null){
            if(playerKeybinds.getSoulgazerAutoToggle() == 2) soulgazeAbility(event.getEntityPlayer(), event.getTarget());
        }
    }

    @SubscribeEvent
    public static void soulgazeKillEntity(LivingDeathEvent event){
        if(event.isCanceled()) return;
        if(event.getEntityLiving() == null) return;

        if(event.getSource().getTrueSource() instanceof EntityPlayer && "player".equals(event.getSource().damageType)){
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ILycanitesTweaksPlayerCapability playerKeybinds = LycanitesTweaksPlayerCapability.getForPlayer(player);
            if(playerKeybinds != null){
                if(playerKeybinds.getSoulgazerAutoToggle() == 3) soulgazeAbility(player, event.getEntityLiving());
            }
        }
    }

    @SubscribeEvent
    public static void soulgazeInteractEntity(PlayerInteractEvent.EntityInteractSpecific event){
        if(event.getEntityPlayer() == null) return;
        boolean hasSoulgazer = false;

        if(event.getItemStack().getItem() instanceof ItemSoulgazer) {
            hasSoulgazer = true;
        }
        else if(event.getHand() == EnumHand.MAIN_HAND && Helpers.hasSoulgazerEquiped(event.getEntityPlayer(), true)){
            hasSoulgazer = true;
            ILycanitesTweaksPlayerCapability playerKeybinds = LycanitesTweaksPlayerCapability.getForPlayer(event.getEntityPlayer());
            if(playerKeybinds != null && playerKeybinds.getSoulgazerManualToggle()) soulgazeAbility(event.getEntityPlayer(), event.getTarget());
        }

        if(!hasSoulgazer || event.getWorld().isRemote) return;

        if(event.getTarget() instanceof EntityPlayer && event.getWorld().rand.nextFloat() < 0.05){
            event.getWorld().playSound(null, event.getPos(), LycanitesTweaksRegistry.SOULGAZER_PLAYER, SoundCategory.PLAYERS, 1F, 1F);
        }
        else{
            IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(event.getEntityPlayer());
            if (pml != null) {
                if(event.getTarget() instanceof EntityBossSummonCrystal) {
                    IEntityStoreCreatureCapability storeCreature = event.getTarget().getCapability(EntityStoreCreatureCapabilityHandler.ENTITY_STORE_CREATURE, null);
                    if(storeCreature != null) {
                        int levels = storeCreature.getStoredCreatureEntity().getLevel();
                        int variant = ((EntityBossSummonCrystal) event.getTarget()).getVariantType();

                        if(CreatureManager.getInstance().getCreature(storeCreature.getStoredCreatureEntity().creatureTypeName) == null)
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal.vanilla",
                                    storeCreature.getStoredCreatureEntity().getDisplayName())
                            );
                        else if(variant == 1 && PlayerMobLevelsConfig.getPmlBonusCategories().containsKey(PlayerMobLevelsConfig.BonusCategory.AltarBossMini)){
                            levels = levels + pml.getTotalLevelsForCategory(PlayerMobLevelsConfig.BonusCategory.AltarBossMini,
                                    (BaseCreatureEntity)storeCreature.getStoredCreatureEntity().entity);
                            if(PlayerMobLevelsConfig.getPmlBonusCategorySoulgazer().contains(PlayerMobLevelsConfig.BonusCategory.AltarBossMini)){
                                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal",
                                        storeCreature.getStoredCreatureEntity().getLevel(),
                                        storeCreature.getStoredCreatureEntity().getDisplayName(),
                                        levels)
                                );
                            }
                            else{
                                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal.nogazer",
                                        levels,
                                        storeCreature.getStoredCreatureEntity().getDisplayName())
                                );
                            }
                        }
                        else if(variant == 2 && PlayerMobLevelsConfig.getPmlBonusCategories().containsKey(PlayerMobLevelsConfig.BonusCategory.DungeonBoss)){
                            levels = levels + pml.getTotalLevelsForCategory(PlayerMobLevelsConfig.BonusCategory.DungeonBoss,
                                    (BaseCreatureEntity)storeCreature.getStoredCreatureEntity().entity);
                            if(PlayerMobLevelsConfig.getPmlBonusCategorySoulgazer().contains(PlayerMobLevelsConfig.BonusCategory.DungeonBoss)){
                                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal",
                                        storeCreature.getStoredCreatureEntity().getLevel(),
                                        storeCreature.getStoredCreatureEntity().getDisplayName(),
                                        levels)
                                );
                            }
                            else{
                                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal.nogazer",
                                        levels,
                                        storeCreature.getStoredCreatureEntity().getDisplayName())
                                );
                            }
                        }
                        else if(variant == -1 && PlayerMobLevelsConfig.getPmlBonusCategories().containsKey(PlayerMobLevelsConfig.BonusCategory.EncounterEvent)){
                            levels = levels + pml.getTotalLevelsForCategory(PlayerMobLevelsConfig.BonusCategory.EncounterEvent,
                                            (BaseCreatureEntity)storeCreature.getStoredCreatureEntity().entity);
                            if(PlayerMobLevelsConfig.getPmlBonusCategorySoulgazer().contains(PlayerMobLevelsConfig.BonusCategory.EncounterEvent)){
                                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal",
                                        storeCreature.getStoredCreatureEntity().getLevel(),
                                        storeCreature.getStoredCreatureEntity().getDisplayName(),
                                        levels)
                                );
                            }
                            else{
                                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal.nogazer",
                                        levels,
                                        storeCreature.getStoredCreatureEntity().getDisplayName())
                                );
                            }
                        }
                        else{
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.soulgazer.playermoblevels.bosscrystal.nogazer",
                                    levels,
                                    storeCreature.getStoredCreatureEntity().getDisplayName())
                            );
                        }
                    }
                }
            }
        }
    }
}
