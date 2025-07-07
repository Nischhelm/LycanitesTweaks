package lycanitestweaks.mixin.forge;

import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.util.IConfigCategoryUnsortedMixin;
import net.minecraftforge.common.config.Configuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/*
 * Apply only to /config/lycanitestweaks.cfg
 * Affects read and write
 */
@Mixin(Configuration.class)
public abstract class ConfigurationMixin {

    @Shadow(remap = false)
    private String fileName;

    @ModifyArgs(
            method = "load",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 1),
            remap = false
    )
    public void lycanitesTweaks_forgeConfiguration_loadElementsUnsortedProperties(Args args){
        if(this.fileName.equals("/config/" + LycanitesTweaks.MODID + ".cfg") && args.get(1) instanceof IConfigCategoryUnsortedMixin){
            ((IConfigCategoryUnsortedMixin) args.get(1)).lycanitestweaks$setUnsorted();
        }
    }

    @ModifyArgs(
            method = "getCategory",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"),
            remap = false
    )
    public void lycanitesTweaks_forgeConfiguration_getCategoryUnsortedProperties(Args args){
        if(this.fileName.equals("/config/" + LycanitesTweaks.MODID + ".cfg") && args.get(1) instanceof IConfigCategoryUnsortedMixin){
            ((IConfigCategoryUnsortedMixin) args.get(1)).lycanitestweaks$setUnsorted();
        }
    }
}
