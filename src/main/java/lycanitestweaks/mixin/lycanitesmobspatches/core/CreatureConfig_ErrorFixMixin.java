package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.lycanitesmobs.core.info.CreatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CreatureConfig.class)
public abstract class CreatureConfig_ErrorFixMixin {

    @ModifyConstant(
            method = "loadConfig",
            constant = @Constant(stringValue = "Elemental Fusion", ordinal = 0),
            remap = false
    )
    public String lycanitesTweaks_lycanitesMobsCreatureConfig_loadConfig0(String constant){
        return constant + " Enabled";
    }

    @ModifyConstant(
            method = "loadConfig",
            constant = @Constant(stringValue = "Elemental Fusion", ordinal = 1),
            remap = false
    )
    public String lycanitesTweaks_lycanitesMobsCreatureConfig_loadConfig1(String constant){
        return constant + " Mix Bonus";
    }
}
