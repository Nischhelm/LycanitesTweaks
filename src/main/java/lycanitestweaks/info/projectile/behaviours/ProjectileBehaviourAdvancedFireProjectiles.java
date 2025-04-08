package lycanitestweaks.info.projectile.behaviours;

import com.google.gson.JsonObject;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.entity.CustomProjectileEntity;
import com.lycanitesmobs.core.info.projectile.ProjectileInfo;
import com.lycanitesmobs.core.info.projectile.ProjectileManager;
import com.lycanitesmobs.core.info.projectile.behaviours.ProjectileBehaviourFireProjectiles;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.Arrays;

public class ProjectileBehaviourAdvancedFireProjectiles extends ProjectileBehaviourFireProjectiles {

    private AimType aimType = AimType.RANDOM;

    private boolean impactOnly = false;
    private float sizeScale = -1;

    public ProjectileBehaviourAdvancedFireProjectiles(){

    }

    @Override
    public void loadFromJSON(JsonObject json) {
        super.loadFromJSON(json);
        if (json.has("aimType")) {
            this.aimType = AimType.get(json.get("aimType").getAsString());
        }
        if(json.has("impactOnly")){
            this.impactOnly = json.get("impactOnly").getAsBoolean();
        }
        if(json.has("sizeScale")){
            this.sizeScale = json.get("sizeScale").getAsFloat();
        }
    }

    @Override
    public void onProjectileUpdate(BaseProjectileEntity projectile) {
        if(!this.impactOnly) super.onProjectileUpdate(projectile);
    }

    @Override
    public BaseProjectileEntity createProjectile(BaseProjectileEntity projectile) {
        ProjectileInfo projectileInfo = ProjectileManager.getInstance().getProjectile(this.projectileName);
        if(projectileInfo == null) {
            return null;
        }
        BaseProjectileEntity childProjectile;

        if(projectile.getThrower() != null) {
            childProjectile = projectileInfo.createProjectile(projectile.getEntityWorld(), projectile.getThrower());
            childProjectile.setPosition(
                    projectile.getPositionVector().x,
                    projectile.getPositionVector().y,
                    projectile.getPositionVector().z
            );
        }
        else {
            childProjectile = projectileInfo.createProjectile(
                    projectile.getEntityWorld(),
                    projectile.getPositionVector().x,
                    projectile.getPositionVector().y,
                    projectile.getPositionVector().z
            );
        }

        if(childProjectile instanceof CustomProjectileEntity) {
            ((CustomProjectileEntity)childProjectile).setParent(projectile);
        }
        if(this.persistentCount > 0 && childProjectile instanceof CustomProjectileEntity) {
            ((CustomProjectileEntity)childProjectile).laserAngle = (360F / this.persistentCount) * ((CustomProjectileEntity)projectile).spawnedProjectiles.size();
            ((CustomProjectileEntity)projectile).spawnedProjectiles.add(childProjectile);
        }

        double motionT = projectile.motionX + projectile.motionY + projectile.motionZ;
        if(projectile.motionX < 0)
            motionT -= projectile.motionX * 2;
        if(projectile.motionY < 0)
            motionT -= projectile.motionY * 2;
        if(projectile.motionZ < 0)
            motionT -= projectile.motionZ * 2;

        double childMotionX = projectile.motionX;
        double childMotionY = projectile.motionY;
        double childMotionZ = projectile.motionZ;

        if(aimType == AimType.RANDOM){
            childMotionX = projectile.motionX / motionT + (projectile.getEntityWorld().rand.nextGaussian() - 0.5D);
            childMotionY = projectile.motionY / motionT + (projectile.getEntityWorld().rand.nextGaussian() - 0.5D);
            childMotionZ = projectile.motionZ / motionT + (projectile.getEntityWorld().rand.nextGaussian() - 0.5D);
        }
        else if(aimType == AimType.TARGET){
            if(projectile.getThrower() instanceof EntityLiving && ((EntityLiving)projectile.getThrower()).getAttackTarget() != null) {
                EntityLivingBase target = ((EntityLiving)projectile.getThrower()).getAttackTarget();
                double angle = 0;
                double targetX = target.posX - projectile.posX;
                double targetZ = target.posZ - projectile.posZ;
                double newX = targetX * Math.cos(angle) - targetZ * Math.sin(angle);
                double newY = targetX * Math.sin(angle) + targetZ * Math.cos(angle);
                targetX = newX + projectile.posX;
                targetZ = newY + projectile.posZ;

                childMotionX = targetX - projectile.posX;
                childMotionY = target.getEntityBoundingBox().minY + (target.height * 0.5D) - projectile.posY;
                childMotionZ = targetZ - projectile.posZ;
            }
        }
        else if(aimType == AimType.NONE){ }

        if(this.sizeScale != -1) childProjectile.setProjectileScale(this.sizeScale);

        childProjectile.shoot(
                childMotionX,
                childMotionY,
                childMotionZ,
                this.velocity,
                0
        );

        projectile.playSound(childProjectile.getLaunchSound(), 1.0F, 1.0F / (projectile.getEntityWorld().rand.nextFloat() * 0.4F + 0.8F));
        projectile.getEntityWorld().spawnEntity(childProjectile);

        return childProjectile;
    }

    private enum AimType{

        RANDOM("random"),
        NONE("none"),
        TARGET("target");

        private final String type;

        AimType(String type){
            this.type = type;
        }

        public static AimType get(String type) {
            return Arrays.stream(AimType.values())
                    .filter(aim -> aim.type.equals(type))
                    .findFirst().orElse(RANDOM);
        }
    }
}
