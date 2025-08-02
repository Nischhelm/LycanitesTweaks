package lycanitestweaks.mixin.lycanitestweaksclient;

import com.lycanitesmobs.client.model.ModelCreatureObj;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelCreatureObj.class)
public abstract class ModelCreatureObj_BigChildHeadMixin {

    @Shadow(remap = false)
    public boolean bigChildHead;

    @Inject(
            method = "<init>(F)V",
            at = @At("RETURN"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsModelCreatureObj_initBigChildHead(float shadowSize, CallbackInfo ci){
        this.bigChildHead = true;
    }
}
