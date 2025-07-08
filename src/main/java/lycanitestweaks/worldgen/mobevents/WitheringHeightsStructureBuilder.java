package lycanitestweaks.worldgen.mobevents;

import com.lycanitesmobs.ExtendedWorld;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.entity.projectile.EntityShadowfireBarrier;
import com.lycanitesmobs.core.mobevent.MobEventPlayerServer;
import com.lycanitesmobs.core.mobevent.effects.StructureBuilder;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WitheringHeightsStructureBuilder extends StructureBuilder {

	public WitheringHeightsStructureBuilder() {
		this.name = LycanitesTweaks.MODID + "_witheringheights";
	}

	@Override
	public void build(World world, EntityPlayer player, BlockPos pos, int level, int ticks, int variant) {
		ExtendedWorld worldExt = ExtendedWorld.getForWorld(world);
		int originX = pos.getX();
		int originY = pos.getY();
		int originZ = pos.getZ();

		originX += 20;
		int height = 40;
		if(originY < 5)
			originY = 5;
		if(world.getHeight() <= height)
			originY = 5;
		else if(originY + height >= world.getActualHeight())
			originY = Math.max(5, world.getActualHeight() - height - 1);

		// Effects:
		if(ticks == 1) {
			for(int i = 0; i < 5; i++) {
				BaseProjectileEntity baseProjectileEntity = new EntityShadowfireBarrier(world, originX, originY + (10 * i), originZ);
				baseProjectileEntity.clientOnly = true;
				baseProjectileEntity.setDamage(0);
				baseProjectileEntity.projectileLife = 20 * 20;
				world.spawnEntity(baseProjectileEntity);
				if(worldExt != null) {
					worldExt.bossUpdate(baseProjectileEntity);
				}
			}
		}

		// Explosions:
		if(ticks >= 10 * 20 && ticks % 10 == 0) {
			world.createExplosion(null, originX - 20 + world.rand.nextInt(40), originY + 25 + world.rand.nextInt(10), originZ - 20 + world.rand.nextInt(40), 2, true);
		}

		// Spawn Boss:
		if(ticks % 20 == 0) {
			EntityWither entitywither = new EntityWither(world);
			entitywither.setLocationAndAngles(originX, originY + 25, originZ, 0, 0);
			entitywither.ignite();
			world.spawnEntity(entitywither);
			if(worldExt != null) {
				MobEventPlayerServer mobEventPlayerServer = worldExt.getMobEventPlayerServer(this.name);
				if(mobEventPlayerServer != null) {
					mobEventPlayerServer.mobEvent.onSpawn(entitywither, world, player, pos, level, ticks, variant);
				}
			}
		}
	}
}
