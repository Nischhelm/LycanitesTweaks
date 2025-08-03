package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.lycanitesmobs.core.info.altar.AltarInfoAmalgalich;
import com.lycanitesmobs.core.info.altar.AltarInfoAsmodeus;
import com.lycanitesmobs.core.info.altar.AltarInfoRahovart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {
        AltarInfoAmalgalich.class,
        AltarInfoAsmodeus.class,
        AltarInfoRahovart.class
})
public abstract class AltarInfoMainBoss_ConsumeSoulcubeMixin  {

    @Inject(
            method = "activate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;<init>(III)V",remap = true),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsAltarInfoMainBoss_activateSoulcubeConsume(Entity entity, World world, BlockPos pos, int variant, CallbackInfoReturnable<Boolean> cir){
        world.setBlockToAir(pos);
    }
}
