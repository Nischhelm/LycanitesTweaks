package lycanitestweaks.mixin.lycanitesmobsfeatures.bosstweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.entity.projectile.EntityDevilGatling;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.potion.PotionVoided;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityDevilGatling.class)
public abstract class EntityDevilGatlingTweaksMixin extends BaseProjectileEntity {

    public EntityDevilGatlingTweaksMixin(World world) {
        super(world);
    }

    @ModifyExpressionValue(
            method = "onDamage",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/info/ObjectLists;inEffectList(Ljava/lang/String;Lnet/minecraft/potion/Potion;)Z"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsEntityDevilGatling_onDamageAnyPurge(boolean original, @Local Potion potion){
        if(ForgeConfigHandler.server.asmodeusConfig.devilGatlingPurgeAnyBuff)
            return !potion.isBadEffect();
        return original;
    }

    @Inject(
            method = "onDamage",
            at = @At("RETURN"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEntityDevilGatling_onDamageAddVoided(EntityLivingBase target, float damage, boolean attackSuccess, CallbackInfo ci){
        if(ForgeConfigHandler.server.asmodeusConfig.devilGatlingVoidedTime > 0)
            target.addPotionEffect(new PotionEffect(PotionVoided.INSTANCE, this.getEffectDuration(ForgeConfigHandler.server.asmodeusConfig.devilGatlingVoidedTime), 0));
    }
}
