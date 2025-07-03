package lycanitestweaks.compat;

import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.fml.common.Loader;

public abstract class ModLoadedUtil {

    private static Boolean rlCombatLoaded = null;
    private static Boolean rltweakerLoaded = null;

    public static boolean isRLCombatLoaded() {
        if(rlCombatLoaded == null){
            rlCombatLoaded = false;
            if(Loader.isModLoaded("bettercombatmod")) {
                String[] arrOfStr = Loader.instance().getIndexedModList().get("bettercombatmod").getVersion().split("\\.");
                try {
                    if (Integer.parseInt(String.valueOf(arrOfStr[1])) < 2) {
                        LycanitesTweaks.LOGGER.warn("bettercombatmod API version lower than 2 found. Disable RLCombat Mod Compatibility to avoid further issues");
                    }
                    else rlCombatLoaded = true;
                } catch (Exception ignored) {
                }
            }
        }
        return rlCombatLoaded;
    }

    public static boolean isRLTweakerLoaded() {
        if(rltweakerLoaded == null) rltweakerLoaded = Loader.isModLoaded("rltweaker");
        return rltweakerLoaded;
    }
}
