package lycanitestweaks.info.projectile.behaviours;

import com.google.gson.JsonObject;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.info.projectile.behaviours.ProjectileBehaviourRandomForce;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.Arrays;

public class ProjectileBehaviourTargetedForce extends ProjectileBehaviourRandomForce {

    private ProjectileBehaviourTargetedForce.AimType aimType = ProjectileBehaviourTargetedForce.AimType.RANDOM;

    private int tickRate = 5;
    private boolean sqDistance = false;

    @Override
    public void loadFromJSON(JsonObject json) {
        super.loadFromJSON(json);
        if (json.has("aimType")) {
            this.aimType = ProjectileBehaviourTargetedForce.AimType.get(json.get("aimType").getAsString());
        }
        if (json.has("tickRate")) {
            this.tickRate = json.get("tickRate").getAsInt();
        }
        if(json.has("sqDistance")){
            this.sqDistance = json.get("sqDistance").getAsBoolean();
        }

    }

    @Override
    public void onProjectileUpdate(BaseProjectileEntity projectile) {
        if(projectile.getEntityWorld().isRemote) {
            return;
        }

        if(projectile.updateTick % tickRate == 0)
            if(this.aimType == AimType.TARGET && projectile.getThrower() instanceof EntityLiving &&
                ((EntityLiving)projectile.getThrower()).getAttackTarget() != null) {

                EntityLivingBase target = ((EntityLiving) projectile.getThrower()).getAttackTarget();

                double angle = 0;
                double targetX = (target.posX - projectile.posX);
                double targetZ = (target.posZ - projectile.posZ);
                double newX = targetX * Math.cos(angle) - targetZ * Math.sin(angle);
                double newY = targetX * Math.sin(angle) + targetZ * Math.cos(angle);
                targetX = newX + projectile.posX;
                targetZ = newY + projectile.posZ;

                double modifier;

                if(sqDistance){
                    modifier = (1 + target.getDistanceSq(projectile));
                }
                else {
                    modifier = (1 + target.getDistance(projectile));
                }

                projectile.addVelocity(
                        (targetX - projectile.posX) * this.force / modifier,
                        (target.getEntityBoundingBox().minY + (target.height * 0.5D) - projectile.posY) * this.force / modifier,
                        (targetZ - projectile.posZ) * this.force / modifier
                );
            }
            else if(this.aimType == AimType.RANDOM) {
                projectile.addVelocity(
                        (0.5D - projectile.getEntityWorld().rand.nextDouble()) * this.force,
                        (0.5D - projectile.getEntityWorld().rand.nextDouble()) * this.force,
                        (0.5D - projectile.getEntityWorld().rand.nextDouble()) * this.force
                );
            }
    }

    private enum AimType{

        RANDOM("random"),
        TARGET("target");

        private final String type;

        AimType(String type){
            this.type = type;
        }

        public static ProjectileBehaviourTargetedForce.AimType get(String type) {
            return Arrays.stream(ProjectileBehaviourTargetedForce.AimType.values())
                    .filter(aim -> aim.type.equals(type))
                    .findFirst().orElse(RANDOM);
        }
    }
}
