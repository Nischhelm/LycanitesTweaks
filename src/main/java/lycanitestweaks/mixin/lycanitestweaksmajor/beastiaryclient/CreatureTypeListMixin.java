package lycanitestweaks.mixin.lycanitestweaksmajor.beastiaryclient;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.gui.beastiary.lists.CreatureTypeList;
import com.lycanitesmobs.core.info.CreatureInfo;
import lycanitestweaks.handlers.ForgeConfigProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreatureTypeList.class)
public abstract class CreatureTypeListMixin {

    // ordinal 0 was mapped to the wrong return, so checks both
    @ModifyReturnValue(
            method = "canListCreature",
            at = @At(value = "RETURN"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsCreatureTypeList_canListCreature(boolean original, @Local(argsOnly = true) CreatureInfo creatureInfo){
        return original && !ForgeConfigProvider.getCreatureBeastiaryBlacklist().contains(creatureInfo.getName());
    }
}
