package lycanitestweaks.mixin.lycanitesmobsfeatures.summoningrework;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.pets.SummonSet;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(SummonSet.class)
public abstract class SummonSetReworkMixin {

    @Shadow(remap = false)
    public ExtendedPlayer playerExt;
    @Shadow(remap = false)
    public String summonType;

    // Never happens with GUI, but might as well cover
    @ModifyReturnValue(
            method = "getVariant",
            at = @At("RETURN"),
            remap = false
    )
    public int lycanitesTweaks_lycanitesSummonSet_getVariant(int original){
        if(this.playerExt.getBeastiary().hasKnowledgeRank(this.summonType, ForgeConfigHandler.server.variantSummonRank)) return original;
        return 0;
    }
}
