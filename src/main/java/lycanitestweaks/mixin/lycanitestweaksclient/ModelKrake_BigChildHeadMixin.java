package lycanitestweaks.mixin.lycanitestweaksclient;

import com.lycanitesmobs.client.model.creature.ModelKrake;
import com.lycanitesmobs.client.model.template.ModelTemplateBiped;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelKrake.class)
public abstract class ModelKrake_BigChildHeadMixin extends ModelTemplateBiped {

    @Inject(
            method = "<init>(F)V",
            at = @At("RETURN"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsModelKrake_initBigChildHead(float shadowSize, CallbackInfo ci){
        this.bigChildHead = true;
    }
}
