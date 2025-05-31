package lycanitestweaks.mixin.lycanitesmobspatches;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.ObjectManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ObjectManager.class)
public abstract class ObjectManagerFluidBucketMixin {

    @Inject(
            method = "addFluid",
            at = @At("RETURN"),
            remap = false
    )
    private static void lycanitesTweaks_lycanitesMobsObjectManager_addFluidForgeBucket(String fluidName, CallbackInfoReturnable<Fluid> cir, @Local Fluid fluid){
        FluidRegistry.addBucketForFluid(fluid);
    }
}
