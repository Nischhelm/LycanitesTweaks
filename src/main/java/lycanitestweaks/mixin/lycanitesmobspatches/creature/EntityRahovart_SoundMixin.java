package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.entity.creature.EntityRahovart;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRahovart.class)
public abstract class EntityRahovart_SoundMixin extends BaseCreatureEntity {

    public EntityRahovart_SoundMixin(World world) {
        super(world);
    }

    @ModifyArg(
            method = "hellfireWaveAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", remap = true),
            remap = false
    )
    public Entity lycanitesTweaks_lycanitesMobsEntityRahovart_hellfireWaveAttackSound(Entity hellfireWave){
        this.playSound(((BaseProjectileEntity)hellfireWave).getLaunchSound(), this.getSoundVolume(), 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        return hellfireWave;
    }

    @ModifyArg(
            method = "hellfireWallUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", remap = true),
            remap = false
    )
    public Entity lycanitesTweaks_lycanitesMobsEntityRahovart_hellfireWallAttackSound(Entity hellfireWall){
        this.playSound(((BaseProjectileEntity)hellfireWall).getLaunchSound(), this.getSoundVolume(), 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        return hellfireWall;
    }

    @ModifyArg(
            method = "hellfireBarrierAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", remap = true),
            remap = false
    )
    public Entity lycanitesTweaks_lycanitesMobsEntityRahovart_hellfireBarrierAttackSound(Entity hellfireBarrier){
        this.playSound(((BaseProjectileEntity)hellfireBarrier).getLaunchSound(), this.getSoundVolume(), 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        return hellfireBarrier;
    }
}
