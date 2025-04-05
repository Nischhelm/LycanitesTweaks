package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMinionPersistenceMixin extends EntityLiving {

    @Shadow(remap = false)
    public abstract void setTemporary(int duration);
    @Shadow(remap = false)
    public abstract EntityLivingBase getMasterTarget();

    public BaseCreatureEntityMinionPersistenceMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "summonMinion",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;onFirstSpawn()V"),
            remap = false
    )
    public void lycanitestweaks_lycanitesBaseCreatureEntity_summonMinion(EntityLivingBase minion, double angle, double distance, CallbackInfo ci){
        if(minion instanceof BaseCreatureEntity) {
            ((BaseCreatureEntity) minion).enablePersistence();
        }
        else if(minion instanceof EntityLiving) ((EntityLiving)minion).enablePersistence();
    }

    // Timing is tricky, don't want to set when a creature is a minion before setting a non null master wish I could just check null and not alive
    // Also despawn is delayed until reload
    @Inject(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;getMasterTarget()Lnet/minecraft/entity/EntityLivingBase;", ordinal = 1, remap = false),
            remap = true
    )
    public void lycanitestweaks_lycanitesBaseCreatureEntity_onLivingUpdateMinionMakeTemporary(CallbackInfo ci){
        if(!this.getMasterTarget().isEntityAlive()) this.setTemporary(1200);
    }
}
