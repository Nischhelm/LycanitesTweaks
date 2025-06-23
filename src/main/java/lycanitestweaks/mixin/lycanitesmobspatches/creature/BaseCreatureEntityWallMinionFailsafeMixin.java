package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityWallMinionFailsafeMixin extends EntityLiving {

    public BaseCreatureEntityWallMinionFailsafeMixin(World world) {
        super(world);
    }

    @Inject(
            method = "summonMinion",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;addMinion(Lnet/minecraft/entity/EntityLivingBase;)Z"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsBaseCreatureEntity_summonMinionTryOutsideWall(EntityLivingBase minion, double angle, double distance, CallbackInfo ci){
        if(minion instanceof EntityLiving && !((EntityLiving) minion).isNotColliding()) {
            minion.moveToBlockPosAndAngles(this.getPosition(), world.rand.nextFloat() * 360.0F, 0.0F);
            minion.motionX = (this.world.rand.nextDouble() - (double)0.5F) * distance / 10D;
            minion.motionZ = (this.world.rand.nextDouble() - (double)0.5F) * distance / 10D;
        }
    }
}
