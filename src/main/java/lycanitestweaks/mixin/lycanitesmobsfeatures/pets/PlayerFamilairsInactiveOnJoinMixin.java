package lycanitestweaks.mixin.lycanitesmobsfeatures.pets;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.pets.PetEntryFamiliar;
import com.lycanitesmobs.core.pets.PlayerFamiliars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerFamiliars.class)
public abstract class PlayerFamilairsInactiveOnJoinMixin {

    @Inject(
            method = "parseFamiliarJSON",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/pets/PetEntryFamiliar;setEntitySize(D)Lcom/lycanitesmobs/core/pets/PetEntry;"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesPlayerFamiliars_parseFamiliarJSON(String jsonString, CallbackInfo ci, @Local PetEntryFamiliar familiarEntry){
        familiarEntry.setSpawningActive(false);
    }
}
