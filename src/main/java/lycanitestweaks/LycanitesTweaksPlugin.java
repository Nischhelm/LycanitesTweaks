package lycanitestweaks;

import lycanitestweaks.handlers.ForgeConfigProvider;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class LycanitesTweaksPlugin implements IFMLLoadingPlugin {

	public LycanitesTweaksPlugin() {
		MixinBootstrap.init();

		// FermiumBooter 1.3.0 can use non-unique @Config.Name fields
		// In 1.2.0 using non-unique between different Sub Instances caused toggle handling to fail and always use defaults

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