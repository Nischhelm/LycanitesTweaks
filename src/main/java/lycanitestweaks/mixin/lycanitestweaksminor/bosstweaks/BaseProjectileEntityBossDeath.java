package lycanitestweaks.mixin.lycanitestweaksminor.bosstweaks;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseProjectileEntity.class)
public abstract class BaseProjectileEntityBossDeath extends EntityThrowable {

    public BaseProjectileEntityBossDeath(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/EntityThrowable;onUpdate()V")
    )
    public void lycanitesTweaks_lycanitesMobsBaseProjectileEntity_onUpdateBossDeadProjectile(CallbackInfo ci){
        if(!this.getEntityWorld().isRemote) {
            if(this.getThrower() instanceof BaseCreatureEntity
                    && ((BaseCreatureEntity) this.getThrower()).isBoss()
                    && !this.getThrower().isEntityAlive())
                this.setDead();
        }
    }
}
