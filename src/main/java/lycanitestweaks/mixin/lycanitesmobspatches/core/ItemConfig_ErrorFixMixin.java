package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.lycanitesmobs.core.info.ItemConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemConfig.class)
public abstract class ItemConfig_ErrorFixMixin {

    @ModifyConstant(
            method = "loadGlobalSettings",
            constant = @Constant(stringValue = "minecraft:prismarine_crystal"),
            remap = false
    )
    private static String lycanitesTweaks_lycanitesMobsItemConfig_loadGlobalSettings(String constant){
        return constant + "s";
    }
}
