package lycanitestweaks.handlers.features.item;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.block.BlockSoulcube;
import com.lycanitesmobs.core.item.special.ItemSoulgazer;
import lycanitestweaks.capability.EntityStoreCreatureCapabilityHandler;
import lycanitestweaks.capability.IEntityStoreCreatureCapability;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapabilityHandler;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.LycanitesTweaksRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemSoulgazerMoreInteractionsHandler {

    @SubscribeEvent
    public static void soulgazeBlockRightClick(PlayerInteractEvent.RightClickBlock event){
        if(event.getEntityPlayer() == null || event.getWorld().isRemote) return;
        EntityPlayer player = event.getEntityPlayer();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

        if(player.getHeldItemMainhand().getItem() instanceof ItemSoulgazer){
            IPlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
            if (pml != null) {
                if (ForgeConfigHandler.featuresMixinConfig.playerMobLevelJSONSpawner && block == Blocks.BED) {
                    int levels = Math.max(1, pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.pmlConfig.pmlSpawnerDegree));
                    String message = LanguageManager.translate("soulgazer.playermoblevels.spawner");
                    message = message.replace("%levels%", "" + levels);
                    player.sendMessage(new TextComponentString(message));
                }
                else if (ForgeConfigHandler.featuresMixinConfig.playerMobLevelMainBosses && block instanceof BlockSoulcube) {
                    int levels = Math.max(1, pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.pmlConfig.pmlSoulboundDegree));
                    String message = LanguageManager.translate("soulgazer.playermoblevels.boss");
                    message = message.replace("%levels%", "" + levels);
                    player.sendMessage(new TextComponentString(message));
                }
                else if (player.isCreative())
                    player.sendMessage(new TextComponentString("Unmodified Levels:" + pml.getTotalLevels()));
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
        else{
            IPlayerMobLevelCapability pml = event.getEntityPlayer().getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
            if (pml != null) {
                if(event.getTarget() instanceof EntityBossSummonCrystal) {
                    IEntityStoreCreatureCapability storeCreature = event.getTarget().getCapability(EntityStoreCreatureCapabilityHandler.ENTITY_STORE_CREATURE, null);
                    if(storeCreature != null) {
                        int levels = Math.max(storeCreature.getStoredCreatureEntity().getLevel(), pml.getTotalLevelsWithDegree(ForgeConfigHandler.server.pmlConfig.pmlBossCrystalDegree));
                        String message = LanguageManager.translate("soulgazer.playermoblevels.bosscrystal");
                        message = message.replace("%minimum%", "" + storeCreature.getStoredCreatureEntity().getLevel());
                        message = message.replace("%name%", storeCreature.getStoredCreatureEntity().getDisplayName());
                        message = message.replace("%levels%", "" + levels);
                        event.getEntityPlayer().sendMessage(new TextComponentString(message));
                    }
                }
            }
        }
    }
}
