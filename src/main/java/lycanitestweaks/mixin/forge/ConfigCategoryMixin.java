package lycanitestweaks.mixin.forge;

import lycanitestweaks.util.IConfigCategoryUnsortedMixin;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Based on original by Nischhelm
 * Used by ConfigurationMixin to apply to LycanitesTweaks only
 * needed for subconfigs to be sorted correctly
 */
@Mixin(ConfigCategory.class)
public abstract class ConfigCategoryMixin implements IConfigCategoryUnsortedMixin {

    @Shadow(remap = false) private Map<String, Property> properties;

    @Unique
    @Override
    public void lycanitestweaks$setUnsorted(){
        this.properties = new LinkedHashMap<>(this.properties);
    }
}
