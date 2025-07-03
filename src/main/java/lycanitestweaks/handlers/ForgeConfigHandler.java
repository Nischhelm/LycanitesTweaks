package lycanitestweaks.handlers;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.config.ClientConfig;
import lycanitestweaks.handlers.config.ClientFeaturesConfig;
import lycanitestweaks.handlers.config.IntegrationConfig;
import lycanitestweaks.handlers.config.MajorFeaturesConfig;
import lycanitestweaks.handlers.config.MinorFeaturesConfig;
import lycanitestweaks.handlers.config.PatchConfig;
import lycanitestweaks.handlers.config.ServerConfig;
import lycanitestweaks.handlers.config.major.PlayerMobLevelsConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = LycanitesTweaks.MODID, category = "general&unsorted")
public class ForgeConfigHandler {

	@Config.Comment("Modify the sorting behavior of main configs whose @Config annotated category is appended with \"&unsorted\".\n" +
			"Makes LycanitesTweaks' config be written to disk in the order it was read in instead of sorting alphabetically.\n" +
			"Forge can not perfectly fix partial or update old configs and will append new config entries.")
	@Config.Name("Modify Forge Config Write Order")
	@Config.RequiresMcRestart
	@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.forgeconfigsort.json")
	public static boolean writeForgeConfigUnsorted = true;

	@Config.Comment("LycanitesTweaks has features that rely on being loaded by Lycanites Mobs.\n" +
			"Makes Lycanites Mobs check and load JSON resources from LycanitesTweaks.\n" +
			"JSONs modifications include default config rebalancing and custom additions.\n" +
			"If disabled, there are no automatic replacements for resources provided by LycanitesTweaks")
	@Config.Name("LycanitesTweaks Default JSON")
	@Config.RequiresMcRestart
	@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinjectdefaultjsonloading.json")
	public static boolean addLycanitesTweaksDefaultJSON = true;

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	@MixinConfig.SubInstance
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Mixins based Client Tweaks")
	@Config.Name("Client Mixins")
	@MixinConfig.SubInstance
	public static final ClientFeaturesConfig clientFeaturesMixinConfig = new ClientFeaturesConfig();

	@Config.Comment("Mixins based Tweaks with highly configurable features")
	@Config.Name("Major Features Mixins")
	@MixinConfig.SubInstance
	public static final MajorFeaturesConfig majorFeaturesConfig = new MajorFeaturesConfig();

	@Config.Comment("Mixins based Tweaks with very basic options")
	@Config.Name("Minor Features Mixins")
	@MixinConfig.SubInstance
	public static final MinorFeaturesConfig minorFeaturesConfig = new MinorFeaturesConfig();

	@Config.Comment("Mod Compatibility\n" +
			"Toggles are enabled by default and will flag Fermium Booter errors, disable when associated mod is not installed")
	@Config.Name("Mod Compatibility")
	public static final IntegrationConfig integrationConfig = new IntegrationConfig();

	@Config.Comment("Enable/Disable Patches for Lycanites Mobs")
	@Config.Name("Toggle Patches")
	@MixinConfig.SubInstance
	public static final PatchConfig mixinPatchesConfig = new PatchConfig();

	@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(LycanitesTweaks.MODID)) {
				PlayerMobLevelsConfig.reset();
				ForgeConfigProvider.reset();
				ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
			}
		}
	}
}