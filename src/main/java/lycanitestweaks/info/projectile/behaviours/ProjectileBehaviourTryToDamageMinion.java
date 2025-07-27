package lycanitestweaks.info.projectile.behaviours;

import com.google.gson.JsonObject;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.info.projectile.behaviours.ProjectileBehaviour;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ProjectileBehaviourTryToDamageMinion extends ProjectileBehaviour {

	/** The check radius. Do not set high, uses getEntitiesWithinAABB **/
	public float radius = 0;

	@Override
	public void loadFromJSON(JsonObject json) {
		if(json.has("radius"))
			this.radius = json.get("radius").getAsFloat();
	}

	@Override
	public boolean canDamage(BaseProjectileEntity projectile, World world, EntityLivingBase target, boolean canDamage) {
		if(projectile.getEntityWorld().isRemote || target == projectile.getThrower()) {
			return canDamage;
		}

		if(projectile.getThrower() instanceof BaseCreatureEntity && target instanceof BaseCreatureEntity){
			BaseCreatureEntity master = (BaseCreatureEntity) projectile.getThrower();
			BaseCreatureEntity minion = (BaseCreatureEntity) target;
			if(minion.hasMaster() && minion.getMasterTarget() == master) master.onTryToDamageMinion(minion, -1);
		}

		return canDamage;
	}

	@Override
	public void onProjectileImpact(BaseProjectileEntity projectile, World world, BlockPos pos) {
		if(this.radius <= 0 || projectile.getEntityWorld().isRemote) {
			return;
		}

		if(projectile.getThrower() instanceof BaseCreatureEntity){
			BaseCreatureEntity master = (BaseCreatureEntity) projectile.getThrower();

			List<BaseCreatureEntity> minions = world.getEntitiesWithinAABB(BaseCreatureEntity.class, new AxisAlignedBB(pos).grow(this.radius));
			minions.forEach(minion -> {
				if(minion.hasMaster() && minion.getMasterTarget() == master) master.onTryToDamageMinion(minion, -1);
			});
		}
	}
}
