package lycanitestweaks.entity.projectile;

import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityChargeArrow extends EntityArrow {

    private BaseProjectileEntity projectile = null;

    public EntityChargeArrow(World world) {
        super(world);
        this.setDamage(0);
        this.setSilent(true);
    }

    public EntityChargeArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setDamage(0);
        this.setSilent(true);
    }

    public EntityChargeArrow(World world, EntityLivingBase shooter) {
        super(world, shooter);
        this.setDamage(0);
        this.setSilent(true);
    }

    public EntityChargeArrow(World world, EntityLivingBase shooter, BaseProjectileEntity projectile) {
        super(world, shooter);
        this.setSilent(true);
        this.projectile = projectile;
        this.setDamage(projectile.damage);
    }

    public void onUpdate() {
        if(!ForgeConfigHandler.server.customStaffConfig.chargeStaffArrowsWorld) {
            this.setDead();
        }
        else {
            super.onUpdate();
            if(this.projectile != null){
                if(this.projectile.isDead) this.setDead();
                if(ForgeConfigHandler.server.customStaffConfig.chargeStaffArrowsTeleport)
                    this.setPosition(this.projectile.posX, this.projectile.posY, this.projectile.posZ);
            }
            else this.setDead();
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }
}
