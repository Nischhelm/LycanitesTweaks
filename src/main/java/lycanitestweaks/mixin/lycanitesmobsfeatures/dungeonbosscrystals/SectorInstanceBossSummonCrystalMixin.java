package lycanitestweaks.mixin.lycanitesmobsfeatures.dungeonbosscrystals;

import com.lycanitesmobs.core.dungeon.instance.SectorConnector;
import com.lycanitesmobs.core.dungeon.instance.SectorInstance;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.spawner.MobSpawn;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.capability.EntityStoreCreatureCapabilityHandler;
import lycanitestweaks.capability.IEntityStoreCreatureCapability;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import lycanitestweaks.storedcreatureentity.StoredCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(SectorInstance.class)
public abstract class SectorInstanceBossSummonCrystalMixin {

    @Shadow(remap = false)
    public SectorConnector parentConnector;
    @Shadow(remap = false)
    protected Vec3i roomSize;

    @Redirect(
            method = "build",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/dungeon/instance/SectorInstance;spawnMob(Lnet/minecraft/world/World;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;Lcom/lycanitesmobs/core/spawner/MobSpawn;Ljava/util/Random;)V"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsSectorInstance_buildSpawnCrystal(SectorInstance instance, World world, ChunkPos chunkPos, BlockPos blockPos, MobSpawn mobSpawn, Random random){
        // Restrict To Chunk Position:
        int chunkOffset = 8;
        if(blockPos.getX() < chunkPos.getXStart() + chunkOffset || blockPos.getX() > chunkPos.getXEnd() + chunkOffset) {
            return;
        }
        if(blockPos.getY() <= 0 || blockPos.getY() >= world.getHeight()) {
            return;
        }
        if(blockPos.getZ() < chunkPos.getZStart() + chunkOffset || blockPos.getZ() > chunkPos.getZEnd() + chunkOffset) {
            return;
        }

        // Spawn Mob:
        LycanitesTweaks.LOGGER.log(Level.DEBUG, "Spawning mob {} at: {} level: {}", mobSpawn, blockPos, this.parentConnector.level);
        EntityLiving entityLiving = mobSpawn.createEntity(world);
        entityLiving.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        mobSpawn.onSpawned(entityLiving, null);

        EntityBossSummonCrystal crystal = new EntityBossSummonCrystal(world);
        world.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
        crystal.setPosition(blockPos.getX() + 0.5F, blockPos.getY() + 1, blockPos.getZ() + 0.5F); // Align ontop of Obsidian
        IEntityStoreCreatureCapability storeCreature = crystal.getCapability(EntityStoreCreatureCapabilityHandler.ENTITY_STORE_CREATURE, null);

        if(storeCreature != null) {
            if (entityLiving instanceof BaseCreatureEntity) {
                BaseCreatureEntity creature = (BaseCreatureEntity) entityLiving;
                storeCreature.setStoredCreatureEntity(StoredCreatureEntity.createFromEntity(crystal, creature)
                        .setPersistant(creature.isPersistant())
                        .setFixate(creature.hasFixateTarget())
                        .setHome(creature.getHomeDistanceMax())
                        .setSpawnAsBoss(creature.spawnedAsBoss)
                        .setTemporary(creature.temporaryDuration)
                        .setMobDropsList(creature.savedDrops)
                        .setBlockProtection(creature.spawnedWithBlockProtection)
                );
            }
            else
                storeCreature.setStoredCreatureEntity(StoredCreatureEntity.createFromEntity(crystal, entityLiving));
            crystal.setShowBottom(true);
            crystal.setSearchDistance(Math.max(3F, Math.max(this.roomSize.getX(), this.roomSize.getZ()) / 2F));
            crystal.setVariantType(2);
        }

        world.spawnEntity(crystal);
    }
}
