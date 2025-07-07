package lycanitestweaks;

import fermiumbooter.FermiumRegistryAPI;
import lycanitestweaks.compat.ModLoadedUtil;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.ForgeConfigProvider;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class LycanitesTweaksPlugin implements IFMLLoadingPlugin {

	public LycanitesTweaksPlugin() {
		MixinBootstrap.init();

		// TODO Test FermiumBooter 1.3.0 for duplicate/non-unique Forge Config field names
		// In 1.2.0 using non-unique between different Sub Instances caused toggle handling to fail and always use defaults
		// Forge Config allows Sub Instances to use non-unique names
		FermiumRegistryAPI.registerAnnotatedMixinConfig(ForgeConfigHandler.class, null);
		FermiumRegistryAPI.enqueueMixin(false, "mixins.lycanitestweaks.forgeconfigsort.json", () -> ForgeConfigHandler.writeForgeConfigUnsorted);

		// Mod Compat - This way doesn't render the error message if people use the default
		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.featureclientbeastiaryddd.json",
		FermiumRegistryAPI.isModPresent("distinctdamagedescriptions") && ForgeConfigHandler.integrationConfig.beastiaryGUIDDD);
		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.switchboxlovearrowfix.json",
		FermiumRegistryAPI.isModPresent("switchbow") && ForgeConfigHandler.integrationConfig.switchbowLoveArrowFix);
		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.potioncorejumpfix.json",
		FermiumRegistryAPI.isModPresent("potioncore") && ForgeConfigHandler.integrationConfig.potionCoreJumpFix);
		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.baublessoulgazer.json",
			() -> ModLoadedUtil.isBaublesLoaded() && ForgeConfigHandler.integrationConfig.soulgazerBauble);
		//
		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.equipmentreachfix.json",
		FermiumRegistryAPI.isModPresent("reachfix") && ForgeConfigHandler.integrationConfig.craftedEquipmentReachFix);
		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.rlcombatequipmentsweep.json",
			() -> ModLoadedUtil.isRLCombatLoaded() && ForgeConfigHandler.integrationConfig.craftedEquipmentRLCombatSweep);
		FermiumRegistryAPI.enqueueMixin(true, "mixins.lycanitestweaks.rlcombatequipmentoffhandforce.json",
			() -> ModLoadedUtil.isRLCombatLoaded() && ForgeConfigHandler.integrationConfig.craftedEquipmentForceRLCombatOffhand);

		ForgeConfigProvider.pluginInit();
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