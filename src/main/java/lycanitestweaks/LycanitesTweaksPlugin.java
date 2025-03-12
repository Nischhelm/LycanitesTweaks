package lycanitestweaks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import fermiumbooter.FermiumRegistryAPI;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class LycanitesTweaksPlugin implements IFMLLoadingPlugin {

	private static final Map<String, String> lateMap = setupLateMap();

	private static Map<String, String> setupLateMap() {
		Map<String, String> map = new HashMap<>();

		// Features
		map.put("Boss DPS Limit Recalc", "mixins.lycanitestweaks.featurebossdamagelimitdpsrecalc.json");
		map.put("Level match minions goal", "mixins.lycanitestweaks.featureaiminionhostlevelmatch.json");
		map.put("Level match minions host method", "mixins.lycanitestweaks.featurebasecreatureminionhostlevelmatch.json");
		map.put("Treat Sets Persistence", "mixins.lycanitestweaks.featuretameabletreatpersistence.json");
		map.put("Crafted Equipment RLCombat Sweep (RLCombat)", "mixins.lycanitestweaks.equipmentrlcombatsweep.json");
		map.put("Crafted Equipment Offhand RMB Needs Sneak", "mixins.lycanitestweaks.equipmentrmbneedssneak.json");
		// Patches
		map.put("Fix AgeableCreature baby drops", "mixins.lycanitestweaks.patchesageablebabydrops.json");
		map.put("Fix BaseCreature Summon Persistence", "mixins.lycanitestweaks.patchesbasecreatureminionpersistence.json");
		map.put("Fix Ettin grief flag", "mixins.lycanitestweaks.patchesettingriefflag.json");
		map.put("Fix Serpix Blizzard Offset", "mixins.lycanitestweaks.patcheserpixblizzardoffset.json");

		return Collections.unmodifiableMap(map);
	}

	public LycanitesTweaksPlugin() {
		MixinBootstrap.init();
		//False for Vanilla/Coremod mixins, true for regular mod mixins
		FermiumRegistryAPI.enqueueMixin(false, "mixins.lycanitestweaks.vanilla.json");
//		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.jei.json", () -> Loader.isModLoaded("jei"));

		LycanitesTweaks.LOGGER.log(Level.INFO, "LycanitesTweaks Late Enqueue Start");
		for(Map.Entry<String, String> entry : lateMap.entrySet()) {
			LycanitesTweaks.LOGGER.log(Level.INFO, "LycanitesTweaks Late Enqueue: " + entry.getKey());
			FermiumRegistryAPI.enqueueMixin(true, entry.getValue(), () -> ForgeConfigHandler.getBoolean(entry.getKey()));
		}
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[0];
	}
	
	@Override
	public String getModContainerClass()
	{
		return null;
	}
	
	@Override
	public String getSetupClass()
	{
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data) { }
	
	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}