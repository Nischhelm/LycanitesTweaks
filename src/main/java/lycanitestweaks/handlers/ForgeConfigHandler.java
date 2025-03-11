package lycanitestweaks.handlers;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import lycanitestweaks.LycanitesTweaks;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = LycanitesTweaks.MODID)
public class ForgeConfigHandler {

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Enable/Disable Tweaks")
	@Config.Name("Toggle Mixins")
	public static final MixinConfig mixinConfig = new MixinConfig();

	@Config.Comment("Enable/Disable Patches")
	@Config.Name("Toggle Patches")
	public static final PatchConfig mixinPatchesConfig = new PatchConfig();

	public static class ClientConfig {

		@Config.Comment("Example client side config option")
		@Config.Name("Example Client Option")
		public boolean exampleClientOption = true;
	}

	public static class ServerConfig {

		@Config.Comment("Example server side config option")
		@Config.Name("Example Server Option")
		public boolean exampleServerOption = true;
	}

	public static class MixinConfig {

		@Config.Comment("Summon minion goal matches host and minion levels (AI Goal)")
		@Config.Name("Level match minions goal")
		@Config.RequiresMcRestart
		public boolean levelMatchMinionsGoal = true;

		@Config.Comment("Host entity summons minions at matching levels (Hard Coded)")
		@Config.Name("Level match minions host method")
		@Config.RequiresMcRestart
		public boolean levelMatchMinionsHostMethod = true;

		@Config.Comment("Cancels Crafted Sweep and rehandle with RLCombat Sweep")
		@Config.Name("Crafted Equipment RLCombat Sweep (RLCombat)")
		@Config.RequiresMcRestart
		public boolean craftedEquipmentRLCombatSweep = true;

		@Config.Comment("Make offhand crafted equipment RMB ability require player to be sneaking")
		@Config.Name("Crafted Equipment Offhand RMB Needs Sneak")
		@Config.RequiresMcRestart
		public boolean craftedEquipmentOffhandRMBSneak = true;
	}

	public static class PatchConfig {

		@Config.Comment("Add persistence to summons via BaseCreature method")
		@Config.Name("Fix BaseCreature Summon Persistence")
		@Config.RequiresMcRestart
		public boolean fixBaseCreatureSummonPersistence = true;

		@Config.Comment("Fix Ettin checking for inverted griefing flag")
		@Config.Name("Fix Ettin grief flag")
		@Config.RequiresMcRestart
		public boolean fixEttinBlockBreak = true;

		@Config.Comment("Fix Serpix Blizzard projectile spawning in the ground")
		@Config.Name("Fix Serpix Blizzard Offset")
		@Config.RequiresMcRestart
		public boolean fixSerpixBlizzardOffset = true;
	}

	@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(LycanitesTweaks.MODID)) {
				ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
			}
		}
	}

	private static File configFile = null;
	private static String configBooleanString = "";

	public static boolean getBoolean(String name) {
		if(configFile==null) {
			configFile = new File("config", LycanitesTweaks.MODID + ".cfg");
			if(configFile.exists() && configFile.isFile()) {
				try (Stream<String> stream = Files.lines(configFile.toPath())) {
					configBooleanString = stream.filter(s -> s.trim().startsWith("B:")).collect(Collectors.joining());
				}
				catch(Exception ex) {
					LycanitesTweaks.LOGGER.log(Level.ERROR, "Failed to parse LycanitesTweaks config: " + ex);
				}
			}
		}
		//If config is not generated or missing entries, don't enable injection on first run
		return configBooleanString.contains("B:\"" + name + "\"=true");
	}
}