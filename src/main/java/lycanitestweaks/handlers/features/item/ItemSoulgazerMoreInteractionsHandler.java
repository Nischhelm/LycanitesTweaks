package lycanitestweaks.handlers.features.item;

import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.item.special.ItemSoulgazer;
import lycanitestweaks.capability.EntityStoreCreatureCapabilityHandler;
import lycanitestweaks.capability.IEntityStoreCreatureCapability;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import lycanitestweaks.entity.item.EntityEncounterSummonCrystal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.LycanitesTweaksRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemSoulgazerMoreInteractionsHandler {

    // Used to send client messages, now only for debugging server side pml
    @SubscribeEvent
    public static void soulgazeBlockRightClick(PlayerInteractEvent.RightClickBlock event){
        if(event.getEntityPlayer() == null || event.getWorld().isRemote) return;
        if(!ForgeConfigHandler.server.pmlConfig.playerMobLevelCapability) return;
        EntityPlayer player = event.getEntityPlayer();

        if(player.isCreative() && player.getHeldItemMainhand().getItem() instanceof ItemSoulgazer){
            IPlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
            if (pml != null) {
                player.sendMessage(new TextComponentTranslation("soulgazer.interact.pmlcreative",
                        pml.getTotalLevels(),
                        pml.getHighestLevelPet()
                ));
            }
        }
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
    public static void soulgazeEntity(PlayerInteractEvent.EntityInteractSpecific event){
        if(event.getWorld().isRemote || event.getEntityPlayer() == null || !(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemSoulgazer)) return;

        if(event.getTarget() instanceof EntityPlayer && event.getWorld().rand.nextFloat() < 0.05){
            event.getWorld().playSound(null, event.getPos(), LycanitesTweaksRegistry.SOULGAZER_PLAYER, SoundCategory.PLAYERS, 1F, 1F);
        }
        else if(ForgeConfigHandler.server.pmlConfig.playerMobLevelCapability){
            IPlayerMobLevelCapability pml = event.getEntityPlayer().getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
            if (pml != null) {
                if(event.getTarget() instanceof EntityBossSummonCrystal) {
                    IEntityStoreCreatureCapability storeCreature = event.getTarget().getCapability(EntityStoreCreatureCapabilityHandler.ENTITY_STORE_CREATURE, null);
                    if(storeCreature != null) {
                        int levels = Math.max(storeCreature.getStoredCreatureEntity().getLevel(), pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.pmlConfig.pmlBossCrystalDegree));

                        if(!ForgeConfigHandler.server.escConfig.bossCrystalPML)
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("soulgazer.playermoblevels.bosscrystal.nogazer",
                                    storeCreature.getStoredCreatureEntity().getLevel(),
                                    storeCreature.getStoredCreatureEntity().getDisplayName())
                            );
                        else if((!ForgeConfigHandler.server.escConfig.encounterCrystalSoulgazerHold && event.getTarget() instanceof EntityEncounterSummonCrystal)
                            || !ForgeConfigHandler.server.escConfig.bossCrystalSoulgazerHold)
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("soulgazer.playermoblevels.bosscrystal.nogazer",
                                    levels,
                                    storeCreature.getStoredCreatureEntity().getDisplayName())
                            );
                        else if(CreatureManager.getInstance().getCreature(storeCreature.getStoredCreatureEntity().creatureTypeName) == null)
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("soulgazer.playermoblevels.bosscrystal.vanilla",
                                    storeCreature.getStoredCreatureEntity().getDisplayName())
                            );
                        else
                            event.getEntityPlayer().sendMessage(new TextComponentTranslation("soulgazer.playermoblevels.bosscrystal",
                                    storeCreature.getStoredCreatureEntity().getLevel(),
                                    storeCreature.getStoredCreatureEntity().getDisplayName(),
                                    levels)
                            );
                    }
                }
            }
        }
    }
}
