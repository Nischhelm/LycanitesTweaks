package lycanitestweaks.mixin.rlcombat;

import bettercombat.mod.util.ConfigurationHandler;
import com.llamalad7.mixinextras.sugar.Local;
import lycanitestweaks.LycanitesTweaks;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ConfigurationHandler.class)
public abstract class ConfigurationHandlerEquipmentOffhandMixin {

    @Shadow(remap = false)
    @Final
    public static ConfigurationHandler.ServerConfig server;

    @Inject(
            method = "initItemListCache",
            at = @At(value = "INVOKE", target = "Ljava/util/List;toArray([Ljava/lang/Object;)[Ljava/lang/Object;", ordinal = 0),
            remap = false
    )
    private static void lycanitesTweaks_rlCombatConfigurationHandler_initItemListCacheAddRuntimeEquipment(CallbackInfo ci, @Local List<Class<?>> classList){
        String className = "com.lycanitesmobs.core.item.equipment.ItemEquipment";
        try {
            classList.add(Class.forName(className));
            LycanitesTweaks.LOGGER.log(Level.INFO, "ItemEquipment added to RLCombat offhandItemClassWhitelist by LycanitesTweaks. You should not be lazy, go use the intended RLCombat config.");
        } catch (ClassNotFoundException exception) {
            LycanitesTweaks.LOGGER.warn("Item Class not found for entry: {}, ignoring.", className);
        }
    }
}
