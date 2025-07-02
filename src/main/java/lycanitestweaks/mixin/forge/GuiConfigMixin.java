package lycanitestweaks.mixin.forge;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Nischhelm
 * needed for main config to be sorted correctly
 */
@Mixin(GuiConfig.class)
public abstract class GuiConfigMixin {

    @WrapWithCondition(
            method = "collectConfigElements",
            at = @At(value = "INVOKE", target = "Ljava/util/List;sort(Ljava/util/Comparator;)V"),
            remap = false
    )
    private static boolean aaam_dontSortConfig(List<IConfigElement> instance, Comparator unused){
        //This constructor is only called once per opening a mods config gui, not for sub configs
        //If it has entries, categories are listed before fields
        //the lang key will always be modid.general(or whatever is set in category).configname, we grab the modid
        if(!instance.isEmpty()){
            String[] langKey = instance.get(0).getLanguageKey().split("\\.");
            if(langKey.length > 1 && langKey[1].split("&")[0].equals("unsorted"))
                return false;
        }
        return true;
    }
}
