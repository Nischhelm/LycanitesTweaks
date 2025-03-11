package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseCreatureEntity.class)
public class BaseCreatureEntityMinionPersistenceMixin {

    @Inject(
            method = "summonMinion",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;onFirstSpawn()V"),
            remap = false
    )
    protected void lycanitestweaks_lycanitesBaseCreatureEntity_summonMinion(EntityLivingBase minion, double angle, double distance, CallbackInfo ci){
        if(minion instanceof BaseCreatureEntity) ((BaseCreatureEntity)minion).enablePersistence();
        else if(minion instanceof EntityLiving) ((EntityLiving)minion).enablePersistence();
    }
}
