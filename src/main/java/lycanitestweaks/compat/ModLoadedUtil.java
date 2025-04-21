package lycanitestweaks.compat;

import net.minecraftforge.fml.common.Loader;

public abstract class ModLoadedUtil {

    private static Boolean rltweakerLoaded = null;

    public static boolean isRLTweakerLoaded() {
        if(rltweakerLoaded == null) rltweakerLoaded = Loader.isModLoaded("rltweaker");
        return rltweakerLoaded;
    }
}
