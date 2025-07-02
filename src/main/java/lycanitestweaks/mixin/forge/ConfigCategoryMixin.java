package lycanitestweaks.mixin.forge;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Nischhelm
 * needed for subconfigs to be sorted correctly
 */
@Mixin(ConfigCategory.class)
public abstract class ConfigCategoryMixin {

    @Shadow(remap = false) private Map<String, Property> properties;
    @Shadow(remap = false) public abstract ConfigCategory getFirstParent();
    @Shadow(remap = false) public abstract String getName();

    @Inject(
            method = "<init>(Ljava/lang/String;Lnet/minecraftforge/common/config/ConfigCategory;)V",
            at = @At("TAIL"),
            remap = false
    )
    private void lycanitesTweaks_forgeConfigCategory_initUnsortedProperties(String name, ConfigCategory parent, CallbackInfo ci){
        //this only works if the main @Config class category string contains "&unsorted"
        String[] checkName = this.getFirstParent().getName().split("&");
        if(checkName.length > 1 && checkName[1].equals("unsorted"))
            this.properties = new LinkedHashMap<>();
    }
}
