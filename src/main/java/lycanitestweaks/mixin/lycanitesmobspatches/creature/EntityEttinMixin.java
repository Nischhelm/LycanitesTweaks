package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.creature.EntityEttin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(com.lycanitesmobs.core.entity.creature.EntityEttin.class)
public abstract class EntityEttinMixin {

    @Redirect(
            method = "onLivingUpdate",
            at = @At(value = "FIELD", target = "Lcom/lycanitesmobs/core/entity/creature/EntityEttin;griefing:Z", remap = false),
            remap = true
    )
    public boolean lycanitestweaks_lycanitesEntityEttin_onLivingUpdate(EntityEttin instance){
        return false;
    }
}
