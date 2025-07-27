package lycanitestweaks.compat;

import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.fml.common.Loader;

public abstract class ModLoadedUtil {

    public static final String DDD_MODID = "distinctdamagedescriptions";
    public static final String DYNAMICSURROUNDINGS_MODID = "dsurround";
    public static final String ICEANDFIRE_MODID = "iceandfire";
    public static final String INCONTROL_MODID = "incontrol";
    public static final String POTIONCORE_MODID = "potioncore";
    public static final String REACHFIX_MODID = "reachfix";
    public static final String SWITCHBOW_MODID = "switchbow";

    public static final String BAUBLES_MODID = "baubles";
    public static final String RLCOMBAT_MODID = "bettercombatmod";
    public static final String RLTWEAKER_MODID = "rltweaker";

    private static Boolean baublesLoaded = null;
    private static Boolean rlCombatLoaded = null;
    private static Boolean rltweakerLoaded = null;

    public static boolean isBaublesLoaded() {
        if(baublesLoaded == null) baublesLoaded = Loader.isModLoaded(BAUBLES_MODID);
        return baublesLoaded;
    }

    public static boolean isRLCombatLoaded() {
        if(rlCombatLoaded == null){
            rlCombatLoaded = false;
            if(Loader.isModLoaded(RLCOMBAT_MODID)) {
                String[] arrOfStr = Loader.instance().getIndexedModList().get(RLCOMBAT_MODID).getVersion().split("\\.");
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
        if(rltweakerLoaded == null) rltweakerLoaded = Loader.isModLoaded(RLTWEAKER_MODID);
        return rltweakerLoaded;
    }
}
