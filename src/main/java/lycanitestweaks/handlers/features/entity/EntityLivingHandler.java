package lycanitestweaks.handlers.features.entity;

import com.lycanitesmobs.ExtendedWorld;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.capability.playermoblevel.IPlayerMobLevelCapability;
import lycanitestweaks.capability.playermoblevel.PlayerMobLevelCapability;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.config.major.PlayerMobLevelsConfig;
import lycanitestweaks.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

public class EntityLivingHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateDPSCalc(LivingDamageEvent event) {
        if (!ForgeConfigHandler.minorFeaturesConfig.bossDPSLimitRecalc) return;

        if (event.getEntityLiving() instanceof BaseCreatureEntity) {
            BaseCreatureEntity boss = (BaseCreatureEntity) event.getEntityLiving();
            boss.onDamage(event.getSource(), event.getAmount());
            if (boss.damageLimit > 0.0F) event.setAmount(Math.min(event.getAmount(), boss.damageLimit));
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(LivingDestroyBlockEvent event) {
        if (!ForgeConfigHandler.server.blockProtectionLivingEvent) return;
        if (event.getState() == null
                || event.isCanceled()
                || event.getEntityLiving() == null
                || event.getEntityLiving().getEntityWorld().isRemote) {
            return;
        }

        ExtendedWorld extendedWorld = ExtendedWorld.getForWorld(event.getEntity().getEntityWorld());
        if (extendedWorld.isBossNearby(new Vec3d(event.getPos()))) {
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
            if (ForgeConfigHandler.client.debugLoggerTick)
                LycanitesTweaks.LOGGER.log(Level.INFO, "Boss prevented block at {}, from being broke by {}", event.getPos(), event.getEntityLiving());
        }
    }

    @SubscribeEvent
    public static void onCreatureSpawn(LivingSpawnEvent.SpecialSpawn event) {
        if(event.getWorld().isRemote) return;
        if(!(event.getEntityLiving() instanceof BaseCreatureEntity)) return;
        BaseCreatureEntity creature = (BaseCreatureEntity) event.getEntityLiving();

        // Random SpawnedAsBoss
        if(event.getWorld().rand.nextFloat() < ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.spawnedAsBossNaturalSpawnChance) {
            if(!CreatureManager.getInstance().creatureGroups.get("animal").hasEntity(creature)) {
                creature.spawnedAsBoss = true;
                creature.damageLimit = BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
                creature.damageMax = BaseCreatureEntity.BOSS_DAMAGE_LIMIT;
                creature.refreshAttributes();
            }
        }

        // Player Mob Levels
        PlayerMobLevelsConfig.BonusCategory category = null;
        if(event.getSpawner() == null && PlayerMobLevelsConfig.getPmlBonusCategories().containsKey(PlayerMobLevelsConfig.BonusCategory.SpawnerNatural)){
            category = PlayerMobLevelsConfig.BonusCategory.SpawnerNatural;
        }
        else if(event.getSpawner() != null && PlayerMobLevelsConfig.getPmlBonusCategories().containsKey(PlayerMobLevelsConfig.BonusCategory.SpawnerTile)){
            category = PlayerMobLevelsConfig.BonusCategory.SpawnerTile;
        }
        if(category == null) return;

        EntityPlayer player = event.getWorld().getClosestPlayerToEntity(event.getEntityLiving(), 128);
        IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(player);

        if(pml == null || !creature.firstSpawn) return;
        if(!PlayerMobLevelsConfig.getPmlBonusCategorySoulgazer().contains(category) || Helpers.hasSoulgazerEquiped(player)){
            creature.onFirstSpawn();
            creature.addLevel(pml.getTotalLevelsForCategory(category, creature));
            if(ForgeConfigHandler.client.debugLoggerTick) LycanitesTweaks.LOGGER.log(Level.INFO, "{} Spawning: {}", category.name(), creature);
        }
    }
}