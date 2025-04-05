package lycanitestweaks.mixin.lycanitesmobsfeatures.lycanitestweaksjsonloader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.Utilities;
import com.lycanitesmobs.core.JSONLoader;
import com.lycanitesmobs.core.info.ModInfo;
import lycanitestweaks.LycanitesTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.Map;

@Mixin(JSONLoader.class)
public abstract class JSONLoaderLycanitesTweaksMixin {

    @Shadow(remap = false)
    public abstract void loadJsonObjects(Gson gson, Path path, Map<String, JsonObject> jsonObjectMap, String mapKey, String jsonType);

    @Inject(
            method = "loadAllJson",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/JSONLoader;loadJsonObjects(Lcom/google/gson/Gson;Ljava/nio/file/Path;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;)V", ordinal = 1),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsJSONLoader_loadJsonObjects(ModInfo groupInfo, String name, String assetPath, String mapKey, boolean loadCustom, CallbackInfo ci, @Local Gson gson, @Local(ordinal = 1) Map<String, JsonObject> defaultJsons){
        if("projectiles".equals(assetPath) || "elements".equals(assetPath)) {
            Path path = Utilities.getAssetPath(LycanitesTweaks.class, LycanitesTweaks.MODID, assetPath);
            this.loadJsonObjects(gson, path, defaultJsons, mapKey, null);
        }
    }
}
